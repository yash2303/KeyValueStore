package com.yashasvi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;

@Getter
public class InMemoryDataStore implements DataStore {
    private ConcurrentHashMap<String, Map<String, String>> keyValueStorage;
    private ConcurrentHashMap<String, String> dataTypes;

    public InMemoryDataStore() {
        this.keyValueStorage = new ConcurrentHashMap<>();
        this.dataTypes = new ConcurrentHashMap<>();
    }

    @Override
    public void put(String key, Map<String, String> attributeKeyValueMap) {
        keyValueStorage.put(key, attributeKeyValueMap);
    }

    @Override
    public Map<String, String> get(String key) {
        return keyValueStorage.get(key);
    }

    @Override
    public void delete(String key) {
        keyValueStorage.remove(key);
    }

    @Override
    public Set<String> getKeys() {
        return keyValueStorage.keySet();
    }

    @Override
    public boolean checkDataType(String attributeKey, String dataType) {
        return dataType.equals(dataTypes.get(attributeKey));
    }

    @Override
    public void updateDataTypeIfAbsent(String attributeKey, String dataType) {
        dataTypes.putIfAbsent(attributeKey, dataType);
    }

    public List<String> search(String attributeKey, String attributeValue) {
        List<String> keys = new ArrayList<>();
        getKeyValueStorage().forEach(2, (Key, Value) -> {
            if (valueHasSearchPair(Value, attributeKey, attributeValue))
                keys.add(Key);
        });
        return keys;
    }

    private boolean valueHasSearchPair(Map<String, String> value, String attributeKey, String attributeValue) {
        String storedAttributeValue = value.get(attributeKey);
        return attributeValue.equals(storedAttributeValue);
    }
}
