package com.store.service.web.controllers.products;

import com.store.dao.repository.mongodb.ProductCharacteristicsDAO;
import com.store.service.web.dto.products.ProductDTO;
import com.store.service.web.dto.products.characteristics.GeneralCharacteristicDTO;
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

//TODO check whether it is possible to use one html page for

@Controller
public class AddCharacteristicController {
    private final HttpSession session;
    private final ProductCharacteristicsDAO productCharacteristicsDAO;
    private final CharacteristicUtility characteristicUtility;
    private final Document characteristicsDocument = new Document();

    @Autowired
    public AddCharacteristicController(HttpSession session,
            ProductCharacteristicsDAO productCharacteristicsDAO,
            CharacteristicUtility characteristicUtility) {
        this.session = session;
        this.productCharacteristicsDAO = productCharacteristicsDAO;
        this.characteristicUtility = characteristicUtility;
    }

    @GetMapping("/add-characteristic")
    private String addCharacteristic(Model model) {
        model.addAttribute("generalCharacteristic", new GeneralCharacteristicDTO());
        model.addAttribute("addMoreCharacteristics");
        return "manager/product/characteristic/add-characteristic";
    }

    @PostMapping("/process-add-characteristic")
    private String processAddCharacteristic(@Valid @ModelAttribute("characteristic")
                                            GeneralCharacteristicDTO generalCharacteristicDTO,
                                            @RequestParam(value = "addMoreCharacteristics", defaultValue = "false")
                                            String addMoreCharacteristics) {
        Document characteristicDetailsDocument = characteristicUtility.characteristicDetailsStringIntoDocument(
                generalCharacteristicDTO.getCharacteristicDetails());
        characteristicsDocument.append(
                generalCharacteristicDTO.getCharacteristicName(),
                characteristicDetailsDocument);
        if (addMoreCharacteristics.replaceAll("\\W+", "").equals("true")) {
            return "redirect:/add-characteristic";
        }
        /*Document productNameCharacterisricsDocument = new Document();*/
        String productName = (String) session.getAttribute("productName");
        characteristicsDocument.append("name", productName);
        productCharacteristicsDAO.insertProductCharacteristics("product_characteristics",
                characteristicsDocument);
        return "redirect:/add-product-option?addMoreProductVariations=false";
    }
}
