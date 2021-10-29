package com.yashasvi.activity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import com.yashasvi.exception.DataTypeException;
import com.yashasvi.model.DataStore;
import com.yashasvi.util.DataTypeIdentifier;

@AllArgsConstructor
public class SearchService {
    private final DataTypeIdentifier dataTypeIdentifier;
    private final DataStore dataStore;

    public List<String> search(@NonNull String attributeKey, @NonNull String attributeValue)
        throws DataTypeException {
        checkDataType(attributeKey, attributeValue);
        return dataStore.search(attributeKey, attributeValue);
    }

    private void checkDataType(String attributeKey, String attributeValue)
        throws DataTypeException {
        String dataType = dataTypeIdentifier.getDataType(attributeValue);
        if (dataType == null) {
            throw new DataTypeException("Unsupported Data Type");
        }
        if (!dataStore.checkDataType(attributeKey, dataType)) {
            throw new DataTypeException("Invalid Data Type");
        }
    }
}
