package com.store.model.monogdb.characteristics;

import java.util.Map;

public class ProductCharacteristics {
    private String productName;
    private Map<String, Map<String, String>> productCharacteristics;

    public String getProductName() {
        return productName;
    }

    public void setId(String productName) {
        this.productName = productName;
    }

    public void setProductCharacteristics(Map<String, Map<String, String>> productCharacteristics) {
        this.productCharacteristics = productCharacteristics;
    }

    public Map<String, Map<String, String>> getCharacteristicsLists(String productName) {
        return productCharacteristics;
    }

    //TODO check if the passed characteristic exists
    public void addCharacteristic(String characteristicName,
                                  Map<String, String> characteristicDetails) {
        //productCharacteristics.getOrDefault(characteristicName,new HashMap<>());
        productCharacteristics.put(characteristicName, characteristicDetails);
    }

    public void changeCharacteristic(String characteristicName,
                                     Map<String, String> characteristicDetails) {
        productCharacteristics.get(characteristicName).clear();
        productCharacteristics.put(characteristicName, characteristicDetails);
    }
}
