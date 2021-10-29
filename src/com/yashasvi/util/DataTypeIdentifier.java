package com.yashasvi.util;

public class DataTypeIdentifier {
    public String getDataType(String input) {
        String dataType = null;

        if (input.matches("\\d+")) {
            dataType = Integer.class.getSimpleName();
        }

        // checking for floating point numbers
        else if (input.matches("\\d*[.]\\d+")) {
            dataType = Double.class.getSimpleName();
        }

        // checking for floating point numbers
        else if (input.matches("true|false")) {
            dataType = Boolean.class.getSimpleName();
        }

        // checking for String
        else {
            dataType = String.class.getSimpleName();
        }
        return dataType;
    }
}
