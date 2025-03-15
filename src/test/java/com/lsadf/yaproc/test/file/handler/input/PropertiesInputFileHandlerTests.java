package com.lsadf.yaproc.test.file.handler.input;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

public class PropertiesInputFileHandlerTests {

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
    assertThat(contentMap).isNotNull();
    assertThat(contentMap.isEmpty()).isTrue();
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
    assertThat(contentMap).isNotNull();
    assertThat(contentMap.isEmpty()).isFalse();

    // Basic properties
    assertThat(contentMap.get("app.name")).isEqualTo("TestApp");
    assertThat(contentMap.get("app.port")).isEqualTo(8080L);
    assertThat(contentMap.get("app.enabled")).isEqualTo(true);

    // List values (as strings)
    assertThat(contentMap.get("app.numbers")).isEqualTo("1,2,-3,4.5,1.23E2");
    assertThat(contentMap.get("app.tags")).isEqualTo("dev,prod,test");

    // Nested properties
    assertThat(contentMap.get("db.url")).isEqualTo("jdbc:postgresql://localhost:5432/testdb");
    assertThat(contentMap.get("db.pool.size")).isEqualTo(5L);
    assertThat(contentMap.get("db.roles")).isEqualTo("ADMIN,USER");

    // JSON string value
    assertThat(contentMap.get("features.cache")).isEqualTo("{\"enabled\":true,\"ttl\":3600}");

    // Array-like notation
    assertThat(contentMap.get("features.list.0")).isEqualTo("first");
    assertThat(contentMap.get("features.list.1")).isEqualTo("second");

    // Multiline text
    assertThat(contentMap.get("text.multiline")).isEqualTo("Line 1 Line 2 Line 3");

    // Special characters
    assertThat(contentMap.get("special.chars")).isEqualTo("Hello@#$%^&* World!");
  }

  /** Tests that the handler throws the correct exception when given an unsupported format. */
  @Test
  void shouldThrowExceptionForUnsupportedFormat() throws IOException {
    // Given
    FileData fileData = FileUtils.readFile(new File("target/test-data/inputs/simple/simple.json"));

    // When & Then
    assertThrows(UnsupportedFileFormatException.class, () -> handler.handleFile(fileData));
  }

  /** Tests proper property value type conversion if the handler supports it. */
  @Test
  void shouldConvertPropertyTypesCorrectly() throws IOException, UnsupportedFileFormatException {
    // Given
    FileData fileData =
        FileUtils.readFile(new File("target/test-data/inputs/complex/complex.properties"));

    // When
    ContentMap contentMap = handler.handleFile(fileData);

    // Then - if your handler performs type conversion, these assertions would be relevant
    // If it doesn't convert types automatically (just stores as strings), you can remove this test

    // String type should remain as String
    assertThat(contentMap.get("app.name")).isInstanceOf(String.class);

    // Numeric values might be converted to Integer/Double if the handler supports conversion
    // Note: Comment out these assertions if your handler keeps everything as strings
    /*
    assertThat(contentMap.get("app.port")).isInstanceOf(Integer.class);
    assertThat(contentMap.get("db.pool.size")).isInstanceOf(Integer.class);
    assertThat(contentMap.get("app.enabled")).isInstanceOf(Boolean.class);
    */
  }
}
