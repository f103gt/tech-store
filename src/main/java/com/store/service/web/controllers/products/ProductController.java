package com.store.service.web.controllers.products;

import com.store.dao.model.product.Category;
import com.store.dao.model.product.Image;
import com.store.dao.model.product.Product;
import com.store.dao.model.product.SpecificProduct;
import com.store.dao.repository.products.CategoryRepository;
import com.store.dao.repository.products.ImageRepository;
import com.store.dao.repository.products.ProductRepository;
import com.store.dao.repository.products.SpecificProductRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.*;

@Controller
public class ProductController {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SpecificProductRepository specificProductRepository;

    @Autowired
    public ProductController(
            ImageRepository imageRepository,
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            SpecificProductRepository specificProductRepository) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.specificProductRepository = specificProductRepository;
    }

    @GetMapping("/categories")
    public String getCategoryURL(@RequestParam("categoryName") String categoryName) {
        return "redirect:/" + categoryName;
    }

    @GetMapping("/{categoryName}")
    public String getProductPage(@PathVariable String categoryName, Model model) {
        Category category = categoryRepository.findCategoryByCategoryName(categoryName);
        List<Product> products = productRepository.getProductsByCategory(category);
        model.addAttribute("products", products);
        return "manager/product/products";
    }

    @GetMapping("/image")
    public void showImage(@Param("productName") String productName,HttpServletResponse response,
                          Model model){
        Product product = productRepository.getProductByProductName(productName);
        SpecificProduct specificProduct = product.getSpecificProducts().iterator().next();
        Optional<Image> primaryImage = specificProduct.getImages().stream()
                .min(Comparator.comparing(Image::getId));
        response.setContentType("image/jpeg,image/jpg,image/png");
        try {
            response.getOutputStream().write(Objects.requireNonNull(
                    primaryImage.map(Image::getImageData).orElse(null)));
            response.getOutputStream().close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

}
