package com.lsadf.yaproc.test.file.handler.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.handler.output.JsonOutputFileHandler;
import com.lsadf.yaproc.file.handler.output.OutputFileHandler;
import com.lsadf.yaproc.file.handler.output.PropertiesOutputFileHandler;
import com.lsadf.yaproc.file.handler.output.YamlOutputFileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for verifying the functionality of the JsonOutputFileHandler. It
 * ensures that JSON files are correctly generated, written, and validated according to expected
 * implementations.
 *
 * <p>JsonOutputFileHandlerTests is responsible for conducting specific test cases to evaluate the
 * behavior of the JSON output file handling operations, including but not limited to writing data,
 * handling exceptions, ensuring output integrity under different conditions, and verifying
 * handler delegation.
 */
class JsonOutputFileHandlerTests {
  @TempDir Path tempDir;

  private JsonOutputFileHandler jsonHandler;
  private OutputFileHandler propertiesHandler;
  private OutputFileHandler yamlHandler;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    jsonHandler = new JsonOutputFileHandler();
    propertiesHandler = new PropertiesOutputFileHandler();
    yamlHandler = new YamlOutputFileHandler();

    // Set up chain of responsibility
    jsonHandler.setNextHandler(propertiesHandler);
    propertiesHandler.setNextHandler(yamlHandler);

    objectMapper = new ObjectMapper();
  }

  /** Tests that the handler correctly writes a simple ContentMap to a JSON file. */
  @Test
  void shouldWriteSimpleJsonFile() throws IOException {
    // Given
    File outputFile = tempDir.resolve("output.json").toFile();
    ContentMap contentMap = new ContentMap();
    contentMap.put("key", "value");
    contentMap.put("number", 42);
    contentMap.put("boolean", true);

    // When
    jsonHandler.handleFile(outputFile, contentMap, false);

    // Then
    assertTrue(outputFile.exists());
    Map<String, Object> readContent = objectMapper.readValue(outputFile, Map.class);
    assertThat(readContent)
        .isNotNull()
        .isNotEmpty()
        .containsEntry("key", "value")
        .containsEntry("number", 42)
        .containsEntry("boolean", true);
  }

  /**
   * Tests that the handler correctly writes a complex ContentMap with nested structures to a JSON
   * file.
   */
  @Test
  void shouldWriteComplexJsonFile() throws IOException {
    // Given
    File outputFile = tempDir.resolve("complex.json").toFile();
    ContentMap contentMap = new ContentMap();
    contentMap.put("string", "Hello World");
    contentMap.put("number", 42);

    // Create nested object
    Map<String, Object> nestedObject = new LinkedHashMap<>();
    nestedObject.put("another_property", "Test");

    // Create array of objects
    Object[] objectArray = new Object[2];
    Map<String, Object> obj1 = new LinkedHashMap<>();
    obj1.put("index", 0);
    objectArray[0] = obj1;

    Map<String, Object> obj2 = new LinkedHashMap<>();
    obj2.put("index", 1);
    obj2.put("another_final_property", "TEST");
    objectArray[1] = obj2;

    nestedObject.put("object_array", objectArray);
    contentMap.put("nested_object", nestedObject);

    // When
    jsonHandler.handleFile(outputFile, contentMap, false);

    // Then
    assertTrue(outputFile.exists());
    Map<String, Object> readContent = objectMapper.readValue(outputFile, Map.class);

    assertThat(readContent)
        .isNotNull()
        .isNotEmpty()
        .containsEntry("string", "Hello World")
        .containsEntry("number", 42);

    Map<String, Object> readNestedObject = (Map<String, Object>) readContent.get("nested_object");
    assertThat(readNestedObject).isNotNull().containsEntry("another_property", "Test");

    List<Object> readObjectArray = (List<Object>) readNestedObject.get("object_array");
    assertThat(readObjectArray).hasSize(2);

    Map<String, Object> readObj1 = (Map<String, Object>) readObjectArray.get(0);
    assertThat(readObj1).containsEntry("index", 0);

    Map<String, Object> readObj2 = (Map<String, Object>) readObjectArray.get(1);
    assertThat(readObj2).containsEntry("index", 1).containsEntry("another_final_property", "TEST");
  }

  /** Tests that the handler overwrites an existing file when force is set to true. */
  @Test
  void shouldOverwriteExistingFileWhenForceIsTrue() throws IOException {
    // Given
    File outputFile = tempDir.resolve("force.json").toFile();

    // Create initial file
    ContentMap initialContent = new ContentMap();
    initialContent.put("initial", "content");
    jsonHandler.handleFile(outputFile, initialContent, false);

    // When
    ContentMap newContent = new ContentMap();
    newContent.put("new", "content");
    jsonHandler.handleFile(outputFile, newContent, true);

    // Then
    assertTrue(outputFile.exists());
    Map<String, Object> readContent = objectMapper.readValue(outputFile, Map.class);
    assertThat(readContent)
        .isNotNull()
        .isNotEmpty()
        .containsEntry("new", "content")
        .doesNotContainKey("initial");
  }

  /**
   * Tests that the handler with force is set to false throws the good exception if output file is
   * existing
   */
  @Test
  void shouldThrowExceptionWhenForceIsFalseAndFileIsExisting() throws IOException {
    // Given
    File outputFile = tempDir.resolve("existing.json").toFile();

    // Create initial file
    ContentMap initialContent = new ContentMap();
    initialContent.put("key", "value");
    jsonHandler.handleFile(outputFile, initialContent, false);

    // When / Then
    ContentMap newContent = new ContentMap();
    newContent.put("newKey", "newValue");

    IOException exception =
        org.junit.jupiter.api.Assertions.assertThrows(
            IOException.class,
            () -> {
              jsonHandler.handleFile(outputFile, newContent, false);
            });

    assertThat(exception).hasMessageContaining("File already exists");
  }

  /** Tests that the handler correctly processes a ContentMap with special characters. */
  @Test
  void shouldHandleSpecialCharactersInJsonContent() throws IOException {
    // Given
    File outputFile = tempDir.resolve("special.json").toFile();
    ContentMap contentMap = new ContentMap();
    contentMap.put("special", "Hello@#$%^&* World!");
    contentMap.put("quotes", "\"quoted text\"");
    contentMap.put("escapes", "Line 1\nLine 2\tTabbed");

    // When
    jsonHandler.handleFile(outputFile, contentMap, false);

    // Then
    assertTrue(outputFile.exists());
    Map<String, Object> readContent = objectMapper.readValue(outputFile, Map.class);
    assertThat(readContent)
        .isNotNull()
        .isNotEmpty()
        .containsEntry("special", "Hello@#$%^&* World!")
        .containsEntry("quotes", "\"quoted text\"")
        .containsEntry("escapes", "Line 1\nLine 2\tTabbed");
  }
}
