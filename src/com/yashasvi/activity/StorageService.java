package com.yashasvi.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import com.yashasvi.exception.DataTypeException;
import com.yashasvi.model.DataStore;
import com.yashasvi.model.Pair;
import com.yashasvi.util.DataTypeIdentifier;

@AllArgsConstructor
public class StorageService {
    private DataStore dataStore;
    private final DataTypeIdentifier dataTypeIdentifier;

    public Map<String, String> get(@NonNull String key) {
        return dataStore.get(key);
    }

    public void delete(@NonNull String key) {
        dataStore.delete(key);
    }

    public List<String> keys() {
        return new ArrayList<>(dataStore.getKeys());
    }

    public void put(@NonNull String key, @NonNull List<Pair<String, String>> listOfAttributePairs)
        throws DataTypeException {
        checkAndUpdateDataTypes(listOfAttributePairs);
        Map<String, String> attributeKeyValueMap = translateListOfAttributePairs(listOfAttributePairs);
        dataStore.put(key, attributeKeyValueMap);
    }

    private Map<String, String> translateListOfAttributePairs(List<Pair<String, String>> listOfAttributePairs) {
        Map<String, String> attributeKeyValueMap = new HashMap<>();
        for (Pair<String, String> attributePair : listOfAttributePairs) {
            attributeKeyValueMap.put(attributePair.getKey(), attributePair.getValue());
        }
        return attributeKeyValueMap;
    }

    private void checkAndUpdateDataTypes(List<Pair<String, String>> listOfAttributePairs) throws DataTypeException {
        for (Pair<String, String> attributePair : listOfAttributePairs) {
            String key = attributePair.getKey();
            String value = attributePair.getValue();
            String dataType = dataTypeIdentifier.getDataType(value);
            if (dataType == null) {
                throw new DataTypeException("Unsupported Data Type");
            }
            dataStore.updateDataTypeIfAbsent(key, dataType);
            if (!dataStore.checkDataType(key, dataType)) {
                throw new DataTypeException("Invalid Data Type");
            }
        }
    }
}
