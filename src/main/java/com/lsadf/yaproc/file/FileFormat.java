package com.lsadf.yaproc.file;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum FileFormat {
  YAML(new String[] {"yml", "yaml"}),
  JSON(new String[] {"json"}),
  PROPERTIES(new String[] {"properties", "prop"});

  private final String[] extensions;

  FileFormat(String[] extensions) {
    this.extensions = extensions;
  }

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

  public static List<String> getValidExtensions() {
    List<String> validExtensions = new ArrayList<>();
    for (FileFormat fileFormat : FileFormat.values()) {
      validExtensions.addAll(Arrays.asList(fileFormat.extensions));
    }

    return validExtensions;
  }
}
