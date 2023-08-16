package com.store.service.web.controllers.products;

import com.store.dao.model.product.Image;
import com.store.dao.model.product.Product;
import com.store.dao.model.product.SpecificProduct;
import com.store.dao.repository.mongodb.ProductCharacteristicsDAO;
import com.store.dao.repository.products.CategoryRepository;
import com.store.dao.repository.products.ImageRepository;
import com.store.dao.repository.products.ProductRepository;
import com.store.dao.repository.products.SpecificProductRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class SpecificProductController {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SpecificProductRepository specificProductRepository;

    private final ProductCharacteristicsDAO productCharacteristicsDAO;

    @Autowired
    public SpecificProductController(
            ImageRepository imageRepository,
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            SpecificProductRepository specificProductRepository,
            ProductCharacteristicsDAO productCharacteristicsDAO) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.specificProductRepository = specificProductRepository;
        this.productCharacteristicsDAO = productCharacteristicsDAO;
    }

    @GetMapping("/specificProduct")
    public String redirectSpecificProduct(@RequestParam("categoryName") String categoryName,
                                          @RequestParam("productName") String productName,
                                          RedirectAttributes redirectAttributes) {
        Product product = productRepository.getProductByProductName(productName);
        Set<SpecificProduct> specificProducts = product.getSpecificProducts();
        SpecificProduct specificProduct = specificProducts.stream().min(Comparator.comparing(SpecificProduct::getId)).get();
        //List<Image> images = imageRepository.findAllBySpecificProduct(specificProductRepository.findBySku(specificProduct.getSku()));
        redirectAttributes.addAttribute("sku", specificProduct.getSku());
        return "redirect:/product/" + categoryName + "/" + productName;

    }

    @GetMapping("/product/{categoryName}/{productName}")
    public String displaySpecificProduct(Model model,
                                         @PathVariable String categoryName,
                                         @PathVariable String productName,
                                         @RequestParam("sku") String specificProductSku,
                                         RedirectAttributes redirectAttributes) {
        Product product = productRepository.getProductByProductName(productName);
        SpecificProduct specificProduct = specificProductRepository.findBySku(specificProductSku);
        List<BigInteger> specificProductImagesId = new java.util.ArrayList<>(
                specificProduct.getImages().stream().map(Image::getId).sorted().toList());
        model.addAttribute("primaryImageId", specificProductImagesId.get(0));
        //specificProductImagesId.remove(0);
        if (!product.getImages().isEmpty()) {
            List<BigInteger> productImagesIds = new java.util.ArrayList<>(
                    product.getImages().stream().map(Image::getId).sorted().toList());
            model.addAttribute("productImagesIds", productImagesIds);
        }

        model.addAttribute("specificProductImagesIds", specificProductImagesId);
        model.addAttribute("specificProduct", specificProduct);
        model.addAttribute("product", product);
        List<SpecificProduct> specificProducts = product.getSpecificProducts().stream()
                .sorted(Comparator.comparing(SpecificProduct::getId)).toList();
        List<BigInteger> specificProductVariationImages = specificProducts.stream()
                .map(SpecificProduct::getImages)
                .map(images -> images.stream().min(Comparator.comparing(Image::getId)).get())
                .map(Image::getId)
                .toList();
        model.addAttribute("productVariationImages", specificProductVariationImages);
        Map<String, Map<String,String>> characteristics = productCharacteristicsDAO.findCharacteristicsByParameter(
                "product_characteristics", "name", productName);
        model.addAttribute("characteristics", characteristics);
        //Set<SpecificProduct> specificProducts = product.getSpecificProducts();
        /*Map<String, Map<String,String>> optionalCharacteristics = productCharacteristicsDAO.findCharacteristicsByParameter(
                "optional_product_characteristics", "sku", );
        model.addAttribute("optionalCharacteristic", optionalCharacteristics);*/
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
