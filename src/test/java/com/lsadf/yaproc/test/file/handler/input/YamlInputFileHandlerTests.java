package com.lsadf.yaproc.test.file.handler.input;

import com.lsadf.yaproc.exception.UnsupportedFileFormatException;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.file.handler.input.InputFileHandler;
import com.lsadf.yaproc.file.handler.input.YamlInputFileHandler;
import com.lsadf.yaproc.util.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for {@link YamlInputFileHandler} that verifies the handler can properly parse YAML
 * files of varying complexity and throws appropriate exceptions for unsupported formats.
 */
class YamlInputFileHandlerTests {
  private final InputFileHandler handler = new YamlInputFileHandler();

  /** Tests that the handler correctly processes a simple YAML file with basic key-value pairs. */
  @Test
  void shouldHandleSimpleYamlFile() throws IOException, UnsupportedFileFormatException {
    // Given
    FileData fileData = FileUtils.readFile(new File("target/test-data/inputs/simple/simple.yaml"));

    // When
    Map<String, Object> contentMap = handler.handleFile(fileData);

    // Then
    assertThat(contentMap).isNotNull().isNotEmpty().containsEntry("key", "value");
  }

  /**
   * Tests that the handler correctly processes a complex YAML file with nested structures,
   * different data types, arrays, and multi-line text.
   */
  @Test
  void shouldHandleComplexYamlFile() throws IOException, UnsupportedFileFormatException {
    // Given
    FileData fileData =
        FileUtils.readFile(new File("target/test-data/inputs/complex/complex.yaml"));

    // When
    ContentMap contentMap = handler.handleFile(fileData);

    // Then
    assertThat(contentMap).isNotNull().isNotEmpty();

    // Test basic structure
    Map<String, Object> app = (Map<String, Object>) contentMap.get("app");
    assertThat(app).isNotNull().containsEntry("name", "TestApp");

    // Test nested config
    Map<String, Object> config = (Map<String, Object>) app.get("config");
    assertThat(config).isNotNull().containsEntry("port", 8080);

    // Test array handling
    List<Object> flags = (List<Object>) config.get("flags");
    assertThat(flags).containsExactly(true, false, null);

    // Test deeply nested structures
    Map<String, Object> nested = (Map<String, Object>) config.get("nested");
    assertThat(nested).isNotNull();

    List<Object> values = (List<Object>) nested.get("values");
    assertThat(values).hasSize(2);

    Map<String, Object> firstValue = (Map<String, Object>) values.get(0);
    assertThat(firstValue).containsEntry("id", 1);

    Map<String, Object> firstData = (Map<String, Object>) firstValue.get("data");
    assertThat(firstData).containsEntry("x", 10).containsEntry("y", 20);

    List<String> firstTags = (List<String>) firstValue.get("tags");
    assertThat(firstTags).containsExactly("a", "b");

    // Test second value with scientific notation
    Map<String, Object> secondValue = (Map<String, Object>) values.get(1);
    Map<String, Object> secondData = (Map<String, Object>) secondValue.get("data");
    assertThat(secondData).containsEntry("x", -30).containsEntry("y", 150.0);

    // Test db settings with URLs and auth
    Map<String, Object> settings = (Map<String, Object>) app.get("settings");
    Map<String, Object> db = (Map<String, Object>) settings.get("db");
    List<String> urls = (List<String>) db.get("urls");
    assertThat(urls).containsExactly("host1:5432", "host2:5432");

    Map<String, Object> auth = (Map<String, Object>) db.get("auth");
    assertThat(auth).containsEntry("user", "admin");
    List<String> roles = (List<String>) auth.get("roles");
    assertThat(roles).containsExactly("READ", "WRITE");

    // Test multi-line text
    assertThat(app).containsEntry("text", "multiple\nlines here\n");

    // Test mixed type array
    List<Object> mixed = (List<Object>) app.get("mixed");
    assertThat(mixed).hasSize(5);
    assertThat(mixed.get(0)).isEqualTo(123);
    assertThat(mixed.get(1)).isEqualTo("str");
    assertThat(mixed.get(2)).isEqualTo(true);
    assertThat(mixed.get(3)).isNull();
    assertThat(mixed.get(4)).isInstanceOf(Map.class);
  }

  /** Tests that the handler throws the correct exception when given an unsupported file format. */
  @Test
  void shouldThrowExceptionForUnsupportedFormat() throws IOException {
    // Given
    FileData fileData =
        FileUtils.readFile(new File("target/test-data/inputs/simple/simple.properties"));

    // When & Then
    assertThrows(UnsupportedFileFormatException.class, () -> handler.handleFile(fileData));
  }
}
