package com.store.controllers.products;

import com.store.model.product.Category;
import com.store.model.product.Image;
import com.store.model.product.Product;
import com.store.repository.mongodb.ProductCharacteristicsDAO;
import com.store.repository.products.ImageRepository;
import com.store.repository.products.ProductRepository;
import com.store.validation.PriceUtility;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Controller
public class SpecificProductController {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    private final ProductCharacteristicsDAO productCharacteristicsDAO;

    private final PriceUtility priceUtility;

    @Autowired
    public SpecificProductController(
            ImageRepository imageRepository,
            ProductRepository productRepository,
            ProductCharacteristicsDAO productCharacteristicsDAO,
            PriceUtility priceUtility) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.productCharacteristicsDAO = productCharacteristicsDAO;
        this.priceUtility = priceUtility;
    }

    @GetMapping("/specificProduct")
    public String redirectSpecificProduct(@RequestParam("productName") String productName,
                                          RedirectAttributes redirectAttributes) {
        Product product = productRepository.getProductByProductName(productName);
        Category category = productRepository.findCategoryByProductName(productName);
        BigDecimal discountRate = priceUtility.calculateDiscountRate(category);
        double price = product.getPrice().doubleValue();
        double discount = product.getPrice().multiply(discountRate).divide(BigDecimal.valueOf(100),
                RoundingMode.CEILING).doubleValue();
;        redirectAttributes.addAttribute("price", price - discount);
        return "redirect:/product/" + category.getCategoryName() + "/" + productName;

    }

    @GetMapping("/product/{categoryName}/{productName}")
    public String displaySpecificProduct(Model model,
                                         @RequestParam("price") String price,
                                         @PathVariable String categoryName,
                                         @PathVariable String productName) {
        Product product = productRepository.getProductByProductName(productName);
        if (!product.getImages().isEmpty()) {
            List<BigInteger> productImagesIds = new java.util.ArrayList<>(
                    product.getImages().stream().map(Image::getId).sorted().toList());
            model.addAttribute("productImagesIds", productImagesIds);
        }
        model.addAttribute("product", product);
        Map<String, Map<String, String>> characteristics = productCharacteristicsDAO.findCharacteristicsByParameter(
                "product_characteristics", "name", productName);
        model.addAttribute("characteristics", characteristics);
        model.addAttribute("price", price);
        return "manager/product/specific-product";
    }

    @GetMapping("/productImage")
    public void showImage(@RequestParam("imageId") String imageId,
                          HttpServletResponse response) {
        Image image = imageRepository.getImageById(BigInteger.valueOf(Long.parseLong(imageId)));
        response.setContentType("image/jpeg,image/jpg,image/png");
        try {
            response.getOutputStream().write(image.getImageData());
            response.getOutputStream().close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
