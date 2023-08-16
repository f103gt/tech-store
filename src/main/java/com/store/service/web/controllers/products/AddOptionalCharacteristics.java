package com.store.service.web.controllers.products;

import com.store.dao.model.product.SpecificProduct;
import com.store.dao.repository.mongodb.ProductCharacteristicsDAO;
import com.store.dao.repository.products.ProductRepository;
import com.store.dao.repository.products.SpecificProductRepository;
import com.store.service.web.dto.products.SpecificProductDTO;
import com.store.service.web.dto.products.characteristics.OptionalCharacteristicDTO;
import com.store.service.web.services.ImageService;
import com.store.utilties.CharacteristicUtility;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
public class AddOptionalCharacteristics {
    private final HttpSession session;
    //private String sku;
    private final CharacteristicUtility characteristicUtility;
    private final SpecificProductRepository specificProductRepository;
    private final ProductCharacteristicsDAO productCharacteristicsDAO;
    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final Document optionalCharacteristics = new Document();


    @Autowired

    public AddOptionalCharacteristics(HttpSession session,
                                      ProductCharacteristicsDAO productCharacteristicsDAO,
                                      CharacteristicUtility characteristicUtility,
                                      SpecificProductRepository specificProductRepository,
                                      ProductRepository productRepository,
                                      ImageService imageService) {
        this.session = session;
        this.productCharacteristicsDAO = productCharacteristicsDAO;
        this.characteristicUtility = characteristicUtility;
        this.specificProductRepository = specificProductRepository;
        this.productRepository = productRepository;
        this.imageService = imageService;
    }

    @GetMapping("/add-product-option")
    public String addProductOption(Model model,
                                   @RequestParam("addMoreProductVariations") String addMoreProductVariations) {
        model.addAttribute("addMoreProductVariations", addMoreProductVariations);
        model.addAttribute("specificProduct", new SpecificProductDTO());
        return "manager/product/add-product-option";
    }

    @PostMapping("/process-add-product-option")
    private String processAddProductOption(
            @Valid @ModelAttribute("specificProduct") SpecificProductDTO specificProductDTO,
            @RequestParam(value = "addMoreProductVariations", defaultValue = "false") String addMoreProductVariations,
            RedirectAttributes redirectAttributes) {
        session.setAttribute("sku",specificProductDTO.getSku());
        String productName = (String) session.getAttribute("productName");
        SpecificProduct specificProduct = new SpecificProduct();
        specificProduct.setSku(specificProductDTO.getSku());
        specificProduct.setQuantity(specificProductDTO.getQuantity());
        specificProduct.setPrice(specificProductDTO.getPrice());
        specificProduct.setProduct(productRepository.getProductByProductName(productName));
        specificProductRepository.save(specificProduct);
        Arrays.stream(specificProductDTO.getImages())
                .forEach(image -> imageService.saveImage(image, null, specificProduct));
        //redirectAttributes.addAttribute("sku",specificProductDTO.getSku());
        if (addMoreProductVariations.replaceAll("\\W+", "").equals("true")) {
            return "redirect:/add-product-option";
        }
        return "redirect:/add-optional-characteristics";
    }

    @GetMapping("/add-optional-characteristics")
    public String addOptionalCharacteristics(Model model,
                                             RedirectAttributes redirectAttributes) {
        model.addAttribute("optionalCharacteristic", new OptionalCharacteristicDTO());
        model.addAttribute("characteristicDetails");
        model.addAttribute("addMoreOptionalCharacteristics");
        //model.addAttribute("sku",sku);
        return "manager/product/characteristic/add-optional-characteristics";
    }

    @PostMapping("/process-add-optional-characteristics")
    String processAddOptionalCharacteristics(@Valid @ModelAttribute("optionalCharacteristic")
                                             OptionalCharacteristicDTO optionalCharacteristicDTO,
                                             @RequestParam(value = "addMoreOptionalCharacteristics", defaultValue = "false")
                                             String addMoreOptionalCharacteristics) {

        Document characteristicDetailsDocument = characteristicUtility.
                characteristicDetailsStringIntoDocument(optionalCharacteristicDTO.getCharacteristicDetails());
        optionalCharacteristics.append(optionalCharacteristicDTO.getCharacteristicName(), characteristicDetailsDocument);
        if (addMoreOptionalCharacteristics.replaceAll("\\W+", "").equals("true")) {
            return "redirect:/add-optional-characteristics";
        }
        optionalCharacteristics.append("sku",(String) session.getAttribute("sku"));
        productCharacteristicsDAO.insertProductCharacteristics("optional_product_characteristics",
                optionalCharacteristics);
        String categoryName = (String) session.getAttribute("categoryName");
        session.removeAttribute("productName");
        session.removeAttribute("categoryName");
        session.removeAttribute("sku");
        return "redirect:/categories?categoryName=" + categoryName;
    }
}
