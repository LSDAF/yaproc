package com.lsadf.yaproc.test.file.handler.output;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileFormat;
import com.lsadf.yaproc.file.handler.output.PropertiesOutputFileHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * This class contains unit tests for verifying the functionality of the
 * PropertiesOutputFileHandler. It ensures that Properties files are correctly generated, written,
 * and validated according to expected implementations.
 *
 * <p>PropertiesOutputFileHandlerTests is responsible for conducting specific test cases to evaluate
 * the behavior of the Properties output file handling operations, including writing data, handling
 * exceptions, and ensuring output integrity under different conditions.
 */
class PropertiesOutputFileHandlerTests {

  @TempDir Path tempDir;

  private PropertiesOutputFileHandler propertiesHandler;
  private ContentMap simpleContentMap;
  private ContentMap complexContentMap;

  @BeforeEach
  void setUp() {
    propertiesHandler = new PropertiesOutputFileHandler();

    // Create a simple content map
    simpleContentMap = new ContentMap();
    simpleContentMap.put("key1", "value1");
    simpleContentMap.put("key2", "value2");
    simpleContentMap.put("key3", "value3");

    // Create a complex content map
    complexContentMap = new ContentMap();
    complexContentMap.put("string", "simpleValue");
    complexContentMap.put("number", 123);
    complexContentMap.put("boolean", true);

    // Add nested structures that will be converted to string representations
    Map<String, Object> nestedMap = new LinkedHashMap<>();
    nestedMap.put("nestedKey1", "nestedValue1");
    nestedMap.put("nestedKey2", 456);
    complexContentMap.put("nested", nestedMap);
  }

  /** Tests that the handler correctly writes a simple ContentMap to a properties file. */
  @Test
  void shouldWriteSimplePropertiesFile() throws IOException {
    // Arrange
    File outputFile = tempDir.resolve("test.properties").toFile();

    // Act
    propertiesHandler.handleFile(outputFile, simpleContentMap, true);

    // Assert
    assertTrue(outputFile.exists());

    Properties loadedProperties = new Properties();
    try (FileInputStream fis = new FileInputStream(outputFile)) {
      loadedProperties.load(fis);
    }

    assertEquals("value1", loadedProperties.getProperty("key1"));
    assertEquals("value2", loadedProperties.getProperty("key2"));
    assertEquals("value3", loadedProperties.getProperty("key3"));
    assertEquals(3, loadedProperties.size());
  }

  /** Tests that the handler correctly writes a complex ContentMap to a properties file. */
  @Test
  void shouldWriteComplexPropertiesFile() throws IOException {
    // Arrange
    File outputFile = tempDir.resolve("complex.properties").toFile();

    // Act
    propertiesHandler.handleFile(outputFile, complexContentMap, true);

    // Assert
    assertTrue(outputFile.exists());

    Properties loadedProperties = new Properties();
    try (FileInputStream fis = new FileInputStream(outputFile)) {
      loadedProperties.load(fis);
    }

    assertEquals("simpleValue", loadedProperties.getProperty("string"));
    assertEquals("123", loadedProperties.getProperty("number"));
    assertEquals("true", loadedProperties.getProperty("boolean"));
    assertEquals(
        "{nestedKey1=nestedValue1, nestedKey2=456}", loadedProperties.getProperty("nested"));
    assertEquals(4, loadedProperties.size());
  }

  /** Tests that the handler overwrites an existing file when force is set to true. */
  @Test
  void shouldOverwriteExistingFileWhenForceIsTrue() throws IOException {
    // Arrange
    File outputFile = tempDir.resolve("overwrite.properties").toFile();

    // First write
    propertiesHandler.handleFile(outputFile, simpleContentMap, true);

    ContentMap newContent = new ContentMap();
    newContent.put("newKey", "newValue");

    // Act - overwrite
    propertiesHandler.handleFile(outputFile, newContent, true);

    // Assert
    Properties loadedProperties = new Properties();
    try (FileInputStream fis = new FileInputStream(outputFile)) {
      loadedProperties.load(fis);
    }

    assertEquals(1, loadedProperties.size());
    assertEquals("newValue", loadedProperties.getProperty("newKey"));
    assertThat(loadedProperties.getProperty("key1")).isNull();
  }

  /**
   * Tests that the handler with force is set to false throws the good exception if output file is
   * existing
   */
  @Test
  void shouldThrowExceptionWhenForceIsFalseAndFileIsExisting() throws IOException {
    // Arrange
    File outputFile = tempDir.resolve("existing.properties").toFile();

    // Create the file
    Files.createFile(outputFile.toPath());

    // Act & Assert
    assertThatThrownBy(() -> propertiesHandler.handleFile(outputFile, simpleContentMap, false))
        .isInstanceOf(IOException.class)
        .hasMessageContaining("File already exists");
  }

  /** Tests that the handler correctly processes a ContentMap with special characters. */
  @Test
  void shouldHandleSpecialCharactersInPropertiesContent() throws IOException {
    // Arrange
    File outputFile = tempDir.resolve("special.properties").toFile();
    ContentMap specialContent = new ContentMap();
    specialContent.put("special", "!@#$%^&*()_+");
    specialContent.put("unicode", "ñáéíóúü");
    specialContent.put("spaces", "This is a test");

    // Act
    propertiesHandler.handleFile(outputFile, specialContent, true);

    // Assert
    Properties loadedProperties = new Properties();
    try (FileInputStream fis = new FileInputStream(outputFile)) {
      loadedProperties.load(fis);
    }

    assertEquals("!@#$%^&*()_+", loadedProperties.getProperty("special"));
    assertEquals("ñáéíóúü", loadedProperties.getProperty("unicode"));
    assertEquals("This is a test", loadedProperties.getProperty("spaces"));
  }

  /** Tests that the handler returns the correct FileFormat. */
  @Test
  void shouldReturnPropertiesFileFormat() {
    // Act & Assert
    assertEquals(FileFormat.PROPERTIES, propertiesHandler.getType());
  }

  /** Tests that the handler creates parent directories when they don't exist. */
  @Test
  void shouldCreateParentDirectoriesWhenTheyDontExist() throws IOException {
    // Arrange
    File outputFile = new File(tempDir.toFile(), "subdir/nested/test.properties");

    // Act
    propertiesHandler.handleFile(outputFile, simpleContentMap, true);

    // Assert
    assertTrue(outputFile.exists());
    assertTrue(outputFile.getParentFile().exists());
  }
}
