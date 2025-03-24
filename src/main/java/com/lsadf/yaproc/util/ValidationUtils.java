package com.lsadf.yaproc.util;

import com.lsadf.yaproc.exception.UnsupportedFileFormatException;
import com.lsadf.yaproc.file.FileFormat;
import lombok.experimental.UtilityClass;

/**
 * Utility class providing validation methods. This class is designed to validate specific input
 * values according to predefined rules and throw appropriate exceptions when validation fails.
 */
@UtilityClass
public class ValidationUtils {

  /**
   * Validates the given file format against a predefined list of supported file extensions. If the
   * file format is not supported, an {@code UnsupportedFileFormatException} is thrown.
   *
   * @param fileFormat the file format to validate; typically represented as a string such as
   *     "json", "yaml", or "properties"
   * @throws UnsupportedFileFormatException if the provided file format is not included in the list
   *     of valid extensions
   */
  public static void validateFileFormat(String fileFormat) {
    if (!FileFormat.getValidExtensions().contains(fileFormat)) {
      throw new UnsupportedFileFormatException("Unsupported file format: " + fileFormat);
    }
  }
}
