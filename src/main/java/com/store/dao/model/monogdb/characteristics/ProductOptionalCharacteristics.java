package com.store.dao.model.monogdb.characteristics;

import java.util.Map;

public class ProductOptionalCharacteristics {
    private String productName;
    public Map<String,Map<String,Map<String,String>>> optionalCharacteristics;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Map<String, Map<String, Map<String, String>>> getOptionalCharacteristics() {
        return optionalCharacteristics;
    }

    public void setOptionalCharacteristics(Map<String, Map<String, Map<String, String>>> optionalCharacteristics) {
        this.optionalCharacteristics = optionalCharacteristics;
    }

    //sku -> characteristicName -> detailName : detail
    public void addOptionalCharacteristic(String productSku,
                                          Map<String,Map<String,String>> optionalCharacteristicsDetail){
        optionalCharacteristics.put(productSku,optionalCharacteristicsDetail);
    }
}
