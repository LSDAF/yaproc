package com.lsadf.yaproc.test.file.handler.input;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.lsadf.yaproc.exception.UnsupportedFileFormatException;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.file.handler.input.InputFileHandler;
import com.lsadf.yaproc.file.handler.input.PropertiesInputFileHandler;
import com.lsadf.yaproc.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PropertiesInputFileHandlerTests {

  private final InputFileHandler handler = new PropertiesInputFileHandler();

  /** Tests that the handler correctly processes an empty properties file. */
  @Test
  void shouldHandleEmptyPropertiesFile() throws IOException, UnsupportedFileFormatException {
    // Given
    FileData fileData =
        FileUtils.readFile(new File("target/test-data/inputs/simple/simple.properties"));

    // When
    Map<String, Object> contentMap = handler.handleFile(fileData);

    // Then
    assertThat(contentMap).isNotNull().isEmpty();
  }

  /**
   * Tests that the handler correctly processes a complex properties file with various property
   * types.
   */
  @Test
  void shouldHandleComplexPropertiesFile() throws IOException, UnsupportedFileFormatException {
    // Given
    FileData fileData =
        FileUtils.readFile(new File("target/test-data/inputs/complex/complex.properties"));

    // When
    ContentMap contentMap = handler.handleFile(fileData);


    // Then
    assertThat(contentMap)
        .isNotNull()
        .isNotEmpty()
        // Basic properties
        .containsEntry("app.name", "TestApp")
        .containsEntry("app.port", 8080L)
        .containsEntry("app.enabled", true)
        // List values (as strings)
        .containsEntry("app.numbers", "1,2,-3,4.5,1.23E2")
        .containsEntry("app.tags", "dev,prod,test")
        .containsEntry("db.url", "jdbc:postgresql://localhost:5432/testdb")
        .containsEntry("db.pool.size", 5L)
        .containsEntry("db.roles", "ADMIN,USER")
        // JSON string value
        .containsEntry("features.cache", "{\"enabled\":true,\"ttl\":3600}")
        // Array-like notation
        .containsEntry("features.list.0", "first")
        .containsEntry("features.list.1", "second")
        // Multiline text
        .containsEntry("text.multiline", "Line 1 Line 2 Line 3")
        // Special characters
        .containsEntry("special.chars", "Hello@#$%^&* World!");
  }

  /** Tests that the handler throws the correct exception when given an unsupported format. */
  @Test
  void shouldThrowExceptionForUnsupportedFormat() throws IOException {
    // Given
    FileData fileData = FileUtils.readFile(new File("target/test-data/inputs/simple/simple.json"));

    // When & Then
    assertThrows(UnsupportedFileFormatException.class, () -> handler.handleFile(fileData));
  }

}
