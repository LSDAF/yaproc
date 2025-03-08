package com.lsadf.yaproc.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ClassUtils {
    public static boolean isNumber(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isBoolean(String value) {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
    }

}
