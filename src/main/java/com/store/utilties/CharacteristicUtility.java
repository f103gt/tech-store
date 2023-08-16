package com.store.utilties;

import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class CharacteristicUtility {

    public Document characteristicDetailsStringIntoDocument(String characteristicDetails){
        Document characteristicDetailsDocument = new Document();
        String[] keyValues = characteristicDetails.split("\r\n");
        for (String keyValue : keyValues) {
            String[] parts = keyValue.split(": ");
            if (parts.length == 2) {
                characteristicDetailsDocument.append(parts[0].trim(), parts[1].trim());
            }
        }
        return characteristicDetailsDocument;
    }
}
