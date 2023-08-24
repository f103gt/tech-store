package com.store.repository.mongodb;

import org.bson.Document;

public class CharacteristicDetailsRepository {
    private final Document characteristicDerails;

    public CharacteristicDetailsRepository(){
        this.characteristicDerails = new Document();
    }

    public Document getCharacteristicDerails(){
        return characteristicDerails;
    }

    public void addCharacteristicDetail(String characteristic, String detail) {
        characteristicDerails.append(characteristic, detail);
    }
}
