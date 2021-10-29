package com.yashasvi.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DataStore {
    void put(String key, Map<String, String> attributeKeyValueMap);
    Map<String, String> get(String key);
    void delete(String key);
    Set<String> getKeys();
    boolean checkDataType(String attributeKey, String dataType);
    void updateDataTypeIfAbsent(String attributeKey, String dataType);
    List<String> search(String attributeKey, String attributeValue);
}
