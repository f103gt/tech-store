package com.store.repository.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductCharacteristicsDAO {
    private final MongoDatabase mongoDatabase;
    private MongoCollection<Document> productCharacteristics;

    @Autowired
    public ProductCharacteristicsDAO(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public void insertProductCharacteristics(String collectionName, Document optionalCharacteristics) {
        this.productCharacteristics = mongoDatabase.getCollection(collectionName);
        productCharacteristics.insertOne(optionalCharacteristics);
    }

    public Map<String, Map<String,String>> findCharacteristicsByParameter(String collectionName, String paramName, String param) {
        BasicDBObject query = new BasicDBObject(paramName, param);
        MongoCursor<Document> cursor = mongoDatabase.
                getCollection(collectionName).find(query).cursor();
        Map<String, Map<String,String>> characteristics = new HashMap<>();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            document.entrySet().stream()
                    .filter(entry -> !entry.getKey().equals(paramName))
                    .filter(entry-> !entry.getKey().equals("_id"))
                    .forEach(entry ->
                            characteristics.put(entry.getKey(), innerDocumentToMap(entry.getValue())));
        }
        return characteristics;
    }

    private Map<String, String> innerDocumentToMap(Object document) {
        if (document instanceof Document) {
            Map<String, String> nestedMap = new HashMap<>();
            ((Document) document).forEach((key, value) -> nestedMap.put(key, (String) value));
            return nestedMap;
        }
        return null;
    }

    public List<Document> getProductCharacteristics(String collectionName, String paramName, String param) {
        BasicDBObject query = new BasicDBObject(paramName, param);
        MongoCursor<Document> cursor = mongoDatabase.
                getCollection(collectionName).find(query).cursor();
        List<Document> characteristics = new ArrayList<>();
        while (cursor.hasNext()) {
            characteristics.add(cursor.next());
        }
        characteristics.remove(characteristics.size() - 1);
        return characteristics;
    }
}
