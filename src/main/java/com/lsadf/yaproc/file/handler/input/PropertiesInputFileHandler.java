package com.lsadf.yaproc.file.handler.input;

import com.lsadf.yaproc.exception.UnsupportedFileFormatException;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.file.FileFormat;
import com.lsadf.yaproc.util.ClassUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Properties;

/**
 * Handles input files with the PROPERTIES format. The class processes property files and converts
 * them into a {@link ContentMap}. If the file format is not supported, it delegates to the next
 * handler in the chain.
 */
public class PropertiesInputFileHandler implements InputFileHandler {
  private InputFileHandler nextHandler;

  /** {@inheritDoc} */
  @Override
  public FileFormat getType() {
    return FileFormat.PROPERTIES;
  }

  /** {@inheritDoc} */
  @Override
  public ContentMap handleFile(FileData fileData) throws IOException {
    String type = fileData.getType();
    if (Arrays.stream(getType().getExtensions()).anyMatch(ext -> ext.equalsIgnoreCase(type))) {

      Properties props = readProperties(fileData.getContent());

      ContentMap contentMap = new ContentMap();
      props.forEach(
          (key, value) -> {
            if (ClassUtils.isNumber(value.toString())) {
              contentMap.put(key.toString(), Long.parseLong(value.toString()));
            } else if (ClassUtils.isBoolean(value.toString())) {
              contentMap.put(key.toString(), Boolean.parseBoolean(value.toString()));
            } else {
              contentMap.put(key.toString(), value);
            }
          });

      return contentMap;
    }
    if (nextHandler == null) {
      throw new UnsupportedFileFormatException("Unsupported file format.");
    }
    return nextHandler.handleFile(fileData);
  }

  @Override
  public void setNextHandler(InputFileHandler nextHandler) {
    this.nextHandler = nextHandler;
  }

  /**
   * Reads a properties file and loads its key-value pairs into a {@link Properties} object.
   * The method skips empty lines and lines that start with comment characters ('#' or '!').
   * It also handles multi-line property values that continue with a trailing backslash ('\').
   *
   * @param content the properties file to read as a string
   * @return a {@link Properties} object containing the key-value pairs from the specified file
   * @throws IOException if an I/O error occurs or if the file contains invalid format
   */
  public static Properties readProperties(String content) throws IOException {
    Properties properties = new Properties();

    try (BufferedReader reader = new BufferedReader(new StringReader(content))) {
      StringBuilder currentLine = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        line = line.trim();

        if (line.isEmpty() || line.startsWith("#") || line.startsWith("!")) {
          continue; // Skip comments and empty lines
        }

        // Handle multi-line values (if previous line ended with '\')
        if (currentLine.length() > 0) {
          currentLine.append(line);
        } else {
          currentLine = new StringBuilder(line);
        }

        // Check if the line continues to the next one
        if (currentLine.toString().endsWith("\\")) {
          currentLine.setLength(currentLine.length() - 1); // Remove trailing '\'
          continue; // Continue reading next line
        }

        // Validate format (must contain '=' or ':')
        if (!currentLine.toString().contains("=") && !currentLine.toString().contains(":")) {
          throw new IOException("Malformed properties file: missing '=' or ':' in line: " + currentLine);
        }

        // Split key and value at first occurrence of '=' or ':'
        String[] parts = currentLine.toString().split("[=:]", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty()) {
          throw new IOException("Malformed properties file: Invalid key-value pair in line: " + currentLine);
        }

        properties.setProperty(parts[0].trim(), parts[1].trim());
        currentLine.setLength(0); // Reset buffer for next entry
      }
    }

    return properties; // Successfully loaded
  }
}
