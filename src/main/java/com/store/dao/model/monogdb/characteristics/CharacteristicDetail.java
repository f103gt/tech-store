package com.store.dao.model.monogdb.characteristics;

public class CharacteristicDetail {

    private String detailName;
    private String detail;

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    /*private final Map<String,String> characteristicDetailsMap;

    public CharacteristicDetails(){
        this.characteristicDetailsMap = new HashMap<>();
    }

    public Map<String, String> getCharacteristicDetailsMap() {
        return characteristicDetailsMap;
    }

    public void addCharacteristicDetailMap(String characteristic, String detail) {
        characteristicDetailsMap.put(characteristic, detail);
    }*/


}
