package com.store.service.web.dto.products.characteristics;

public class GeneralCharacteristicDTO {
    //TODO add input validation
    private String characteristicName;
    private String characteristicDetails;

    public String getCharacteristicName() {
        return characteristicName;
    }

    public void setCharacteristicName(String characteristicName) {
        this.characteristicName = characteristicName;
    }

    public String getCharacteristicDetails() {
        return characteristicDetails;
    }

    public void setCharacteristicDetails(String characteristicDetails) {
        this.characteristicDetails = characteristicDetails;
    }
}
