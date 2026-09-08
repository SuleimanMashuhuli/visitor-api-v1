package com.abcbank.visitor.utils;

public final class MaskingUtil {
    
    private MaskingUtil() {}


    public static String mask(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }

        if(value.length() <= 3) {
            return "*".repeat(value.length());
        }

        return value.substring(0, 4)
                    + "***"
                    + value.substring(value.length() - 3);
    }
}
