package com.store.model.monogdb.characteristics;

public class Characteristic {
    private String characteristicName;
    private String characteristicIcon;

    //private Set<CharacteristicDetail> details;

    /*public Set<CharacteristicDetail> getDetails() {
        return details;
    }*/

    /*public void setDetails(Set<CharacteristicDetail> details) {
        this.details = details;
    }*/

    public String getCharacteristicName() {
        return characteristicName;
    }

    public void setCharacteristicName(String characteristicName) {
        this.characteristicName = characteristicName;
    }

    public String getCharacteristicIcon() {
        return characteristicIcon;
    }

    public void setCharacteristicIcon(String characteristicIcon) {
        this.characteristicIcon = characteristicIcon;
    }
}
