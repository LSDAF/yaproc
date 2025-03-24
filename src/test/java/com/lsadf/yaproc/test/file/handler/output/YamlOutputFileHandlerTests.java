package com.lsadf.yaproc.test.file.handler.output;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileFormat;
import com.lsadf.yaproc.file.handler.output.YamlOutputFileHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.yaml.snakeyaml.Yaml;

/**
 * This class contains unit tests for verifying the functionality of the YamlOutputFileHandler. It
 * ensures that YAML files are correctly generated, written, and validated according to expected
 * implementations.
 *
 * <p>YamlOutputFileHandlerTests is responsible for conducting specific test cases to evaluate the
 * behavior of the YAML output file handling operations, including writing data, handling
 * exceptions, and ensuring output integrity under different conditions.
 */
class YamlOutputFileHandlerTests {

  @TempDir Path tempDir;

  private YamlOutputFileHandler yamlHandler;
  private ContentMap simpleContentMap;
  private ContentMap complexContentMap;
  private Yaml yaml;

  @BeforeEach
  void setUp() {
    yamlHandler = new YamlOutputFileHandler();
    yaml = new Yaml();

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

    // Add nested structures
    Map<String, Object> nestedMap = new LinkedHashMap<>();
    nestedMap.put("nestedKey1", "nestedValue1");
    nestedMap.put("nestedKey2", 456);
    complexContentMap.put("nested", nestedMap);

    // Add a list
    List<String> stringList = Arrays.asList("item1", "item2", "item3");
    complexContentMap.put("list", stringList);
  }

  /** Tests that the handler correctly writes a simple ContentMap to a YAML file. */
  @Test
  void shouldWriteSimpleYamlFile() throws IOException {
    // Arrange
    File outputFile = tempDir.resolve("test.yml").toFile();

    // Act
    yamlHandler.handleFile(outputFile, simpleContentMap, true);

    // Assert
    assertTrue(outputFile.exists());

    Map<String, Object> loadedYaml;
    try (FileInputStream fis = new FileInputStream(outputFile)) {
      loadedYaml = yaml.load(fis);
    }

    assertEquals("value1", loadedYaml.get("key1"));
    assertEquals("value2", loadedYaml.get("key2"));
    assertEquals("value3", loadedYaml.get("key3"));
    assertEquals(3, loadedYaml.size());
  }

  /**
   * Tests that the handler correctly writes a complex ContentMap with nested structures to a YAML
   * file.
   */
  @Test
  void shouldWriteComplexYamlFile() throws IOException {
    // Arrange
    File outputFile = tempDir.resolve("complex.yaml").toFile();

    // Act
    yamlHandler.handleFile(outputFile, complexContentMap, true);

    // Assert
    assertTrue(outputFile.exists());

    Map<String, Object> loadedYaml;
    try (FileInputStream fis = new FileInputStream(outputFile)) {
      loadedYaml = yaml.load(fis);
    }

    assertEquals("simpleValue", loadedYaml.get("string"));
    assertEquals(123, loadedYaml.get("number"));
    assertEquals(true, loadedYaml.get("boolean"));

    // Verify nested map
    Map<String, Object> nestedMap = (Map<String, Object>) loadedYaml.get("nested");
    assertEquals("nestedValue1", nestedMap.get("nestedKey1"));
    assertEquals(456, nestedMap.get("nestedKey2"));

    // Verify list
    List<String> list = (List<String>) loadedYaml.get("list");
    assertEquals(Arrays.asList("item1", "item2", "item3"), list);
  }

  /** Tests that the handler overwrites an existing file when force is set to true. */
  @Test
  void shouldOverwriteExistingFileWhenForceIsTrue() throws IOException {
    // Arrange
    File outputFile = tempDir.resolve("overwrite.yaml").toFile();

    // First write
    yamlHandler.handleFile(outputFile, simpleContentMap, true);

    ContentMap newContent = new ContentMap();
    newContent.put("newKey", "newValue");

    // Act - overwrite
    yamlHandler.handleFile(outputFile, newContent, true);

    // Assert
    Map<String, Object> loadedYaml;
    try (FileInputStream fis = new FileInputStream(outputFile)) {
      loadedYaml = yaml.load(fis);
    }

    assertEquals(1, loadedYaml.size());
    assertEquals("newValue", loadedYaml.get("newKey"));
    assertThat(loadedYaml.get("key1")).isNull();
  }

  /**
   * Tests that the handler with force is set to false throws the good exception if output file is
   * existing
   */
  @Test
  void shouldThrowExceptionWhenForceIsFalseAndFileIsExisting() throws IOException {
    // Arrange
    File outputFile = tempDir.resolve("existing.yaml").toFile();

    // Create the file
    Files.createFile(outputFile.toPath());

    // Act & Assert
    assertThatThrownBy(() -> yamlHandler.handleFile(outputFile, simpleContentMap, false))
        .isInstanceOf(IOException.class)
        .hasMessageContaining("exists");
  }

  /** Tests that the handler correctly processes a ContentMap with special characters. */
  @Test
  void shouldHandleSpecialCharactersInYamlContent() throws IOException {
    // Arrange
    File outputFile = tempDir.resolve("special.yaml").toFile();
    ContentMap specialContent = new ContentMap();
    specialContent.put("special", "!@#$%^&*()_+");
    specialContent.put("unicode", "ñáéíóúü");
    specialContent.put("multiline", "Line 1\nLine 2\nLine 3");
    specialContent.put("quotationMarks", "\"Quoted text\"");

    // Act
    yamlHandler.handleFile(outputFile, specialContent, true);

    // Assert
    Map<String, Object> loadedYaml;
    try (FileInputStream fis = new FileInputStream(outputFile)) {
      loadedYaml = yaml.load(fis);
    }

    assertEquals("!@#$%^&*()_+", loadedYaml.get("special"));
    assertEquals("ñáéíóúü", loadedYaml.get("unicode"));
    assertEquals("Line 1\nLine 2\nLine 3", loadedYaml.get("multiline"));
    assertEquals("\"Quoted text\"", loadedYaml.get("quotationMarks"));
  }

  /** Tests that the handler returns the correct FileFormat. */
  @Test
  void shouldReturnYamlFileFormat() {
    // Act & Assert
    assertEquals(FileFormat.YAML, yamlHandler.getType());
  }

  /** Tests that the handler creates parent directories when they don't exist. */
  @Test
  void shouldCreateParentDirectoriesWhenTheyDontExist() throws IOException {
    // Arrange
    File outputFile = new File(tempDir.toFile(), "subdir/nested/test.yaml");

    // Act
    yamlHandler.handleFile(outputFile, simpleContentMap, true);

    // Assert
    assertTrue(outputFile.exists());
    assertTrue(outputFile.getParentFile().exists());
  }

  /** Tests that the handler correctly handles both .yml and .yaml file extensions. */
  @Test
  void shouldHandleBothYmlAndYamlExtensions() throws IOException {
    // Arrange
    File ymlFile = tempDir.resolve("test.yml").toFile();
    File yamlFile = tempDir.resolve("test.yaml").toFile();

    // Act
    yamlHandler.handleFile(ymlFile, simpleContentMap, true);
    yamlHandler.handleFile(yamlFile, simpleContentMap, true);

    // Assert
    assertTrue(ymlFile.exists());
    assertTrue(yamlFile.exists());

    // Verify .yml content
    Map<String, Object> loadedYml;
    try (FileInputStream fis = new FileInputStream(ymlFile)) {
      loadedYml = yaml.load(fis);
    }
    assertEquals(3, loadedYml.size());
    assertEquals("value1", loadedYml.get("key1"));

    // Verify .yaml content
    Map<String, Object> loadedYaml;
    try (FileInputStream fis = new FileInputStream(yamlFile)) {
      loadedYaml = yaml.load(fis);
    }
    assertEquals(3, loadedYaml.size());
    assertEquals("value1", loadedYaml.get("key1"));
  }
}
