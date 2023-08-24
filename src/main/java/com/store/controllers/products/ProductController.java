package com.store.controllers.products;

import com.store.model.product.Category;
import com.store.model.product.Image;
import com.store.model.product.Product;
import com.store.repository.products.CategoryRepository;
import com.store.repository.products.ProductRepository;
import com.store.validation.PriceUtility;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PriceUtility priceUtility;

    @Autowired
    public ProductController(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            PriceUtility priceUtility) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.priceUtility = priceUtility;
    }

    @GetMapping("/categories")
    public String getCategoryURL(@RequestParam("categoryName") String categoryName) {
        return "redirect:/" + categoryName;
    }

    @GetMapping("/{categoryName}")
    public String getProductPage(@PathVariable String categoryName, Model model) {
        Category category = categoryRepository.findCategoryByCategoryName(categoryName);
        List<Product> products = productRepository.getProductsByCategory(category);
        BigDecimal discountRate = priceUtility.calculateDiscountRate(category);
        products = products.stream().filter(product -> product.getQuantity() > 0).toList();
        model.addAttribute("products", products);
        model.addAttribute("discountRate", discountRate.intValue());
        return "manager/product/products";
    }

    @GetMapping("/image")
    public void showImage(@RequestParam("productName") String productName, HttpServletResponse response) {
        Product product = productRepository.getProductByProductName(productName);
        Image primaryImage = product.getImages().stream()
                .filter(Image::isPrimaryImage).
                findFirst().orElse(null);
        response.setContentType("image/jpeg,image/jpg,image/png");
        if (primaryImage != null) {
            try {
                response.getOutputStream().write(primaryImage.getImageData());
                response.getOutputStream().close();
            } catch (IOException e) {
                e.getStackTrace();
            }
        }

    }

}
