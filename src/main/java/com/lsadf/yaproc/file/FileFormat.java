package com.lsadf.yaproc.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

/**
 * Enum representing different file formats and their corresponding extensions.
 *
 * <p>Each file format is associated with one or more extensions, enabling easy identification of
 * the format based on a file's extension. This enum provides methods to determine a file format
 * from an extension and to retrieve a list of all valid extensions.
 *
 * <p>Features: - Contains multiple predefined file formats such as YAML, JSON, and PROPERTIES. -
 * Supports matching extensions case-insensitively to identify the associated file format. -
 * Provides functionality to retrieve all extensions associated with available file formats.
 */
@Getter
public enum FileFormat {
  YAML(new String[] {"yml", "yaml"}),
  JSON(new String[] {"json"}),
  PROPERTIES(new String[] {"properties", "prop"});

  private final String[] extensions;

  FileFormat(String[] extensions) {
    this.extensions = extensions;
  }

  /**
   * Retrieves the {@code FileFormat} associated with the specified file extension.
   *
   * <p>This method iterates through all available file formats and matches the provided
   */
  public static FileFormat fromExtension(String extension) {
    for (FileFormat fileFormat : FileFormat.values()) {
      for (String ext : fileFormat.extensions) {
        if (ext.equalsIgnoreCase(extension)) {
          return fileFormat;
        }
      }
    }
    throw new IllegalArgumentException("Unknown file format: " + extension);
  }

  /**
   * Retrieves a list of all valid file extensions defined in the {@code FileFormat} enum.
   *
   * <p>This method iterates through all declared {@code FileFormat} values and collects their
   * associated extensions into a single list. The resulting list includes all extensions supported
   * across all file formats.
   *
   * @return a list of strings representing all valid file extensions
   */
  public static List<String> getValidExtensions() {
    List<String> validExtensions = new ArrayList<>();
    for (FileFormat fileFormat : FileFormat.values()) {
      validExtensions.addAll(Arrays.asList(fileFormat.extensions));
    }

    return validExtensions;
  }
}
