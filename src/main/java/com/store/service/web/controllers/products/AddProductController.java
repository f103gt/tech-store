package com.store.service.web.controllers.products;

import com.store.dao.model.product.Category;
import com.store.dao.model.product.Product;
import com.store.dao.repository.products.CategoryRepository;
import com.store.dao.repository.products.GeneralDAO;
import com.store.dao.repository.products.ProductRepository;
import com.store.service.web.dto.products.ProductDTO;
import com.store.service.web.services.ImageService;
import com.store.utilties.ImageUtility;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Controller
public class AddProductController {

    private final HttpSession session;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ImageService imageService;

    @Autowired
    public AddProductController(HttpSession session,
                                ImageUtility imageUtility,
                                GeneralDAO generalDAO,
                                ImageService imageService,
                                CategoryRepository categoryRepository,
                                ProductRepository productRepository) {
        this.session = session;
        this.imageService = imageService;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/add-product")
    public String addProduct(Model model) {
        model.addAttribute("product", new ProductDTO());
        return "manager/product/add-product";
    }


    @PostMapping(path = "/process-add-product", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            headers = "content-type=multipart/form-data")
    public String processAddProduct(@Valid @ModelAttribute("product") ProductDTO productDTO) {
        Product product = new Product();
        Category category = categoryRepository.findCategoryByCategoryName(productDTO.getCategoryName());
        product.setCategory(category);
        product.setProductName(productDTO.getProductName());
        productRepository.save(product);
        Arrays.stream(productDTO.getImages())
                .forEach(image -> imageService.saveImage(image, product, null));
        session.setAttribute("productName", productDTO.getProductName());
        session.setAttribute("productCategory", productDTO.getCategoryName());
        return "redirect:/add-characteristic";
    }
}
