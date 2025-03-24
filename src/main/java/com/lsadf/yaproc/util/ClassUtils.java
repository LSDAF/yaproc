package com.lsadf.yaproc.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class providing common methods for class-related operations. This class contains methods
 * for determining the type of values, specifically whether a given string represents a number or a
 * boolean.
 */
@UtilityClass
public class ClassUtils {
  /**
   * Determines whether the given string represents a valid number.
   *
   * @param value the string to validate as a potential numeric value
   * @return true if the string can be successfully parsed as a number, false otherwise
   */
  public static boolean isNumber(String value) {
    try {
      Long.parseLong(value);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Determines whether the given string represents a boolean value. A boolean value is considered
   * as "true" or "false" (case-insensitive).
   *
   * @param value the string to evaluate
   * @return true if the string matches "true" or "false" (case-insensitive), false otherwise
   */
  public static boolean isBoolean(String value) {
    return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
  }
}
