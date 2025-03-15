package com.lsadf.yaproc.test.file.handler.input;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.lsadf.yaproc.exception.UnsupportedFileFormatException;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.file.handler.input.InputFileHandler;
import com.lsadf.yaproc.file.handler.input.JsonInputFileHandler;
import com.lsadf.yaproc.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class JsonInputFileHandlerTests {

  private final InputFileHandler handler = new JsonInputFileHandler();

  @Test
  void shouldHandleSimpleJsonFile() throws IOException, UnsupportedFileFormatException {
    // Given
    FileData fileData = FileUtils.readFile(new File("target/test-data/inputs/simple/simple.json"));

    // When
    Map<String, Object> contentMap = handler.handleFile(fileData);

    // Then
    assertThat(contentMap).isNotNull();
    assertThat(contentMap.isEmpty()).isFalse();
    assertThat(contentMap.get("key")).isEqualTo("value");
  }

  @Test
  void shouldHandleComplexJsonFile() throws IOException, UnsupportedFileFormatException {
    // Given
    FileData fileData =
        FileUtils.readFile(new File("target/test-data/inputs/complex/complex.json"));

    // When
    ContentMap contentMap = handler.handleFile(fileData);

    // Then
    assertThat(contentMap).isNotNull();
    assertThat(contentMap.isEmpty()).isFalse();

    // Test nested object property
    var nestedObject = (LinkedHashMap<String, Object>) contentMap.get("nested_object");
    var anotherProperty = nestedObject.get("another_property");
    var objectArray = (List<Object>) nestedObject.get("object_array");
    assertThat(anotherProperty).isEqualTo("Test");
    assertThat(objectArray.size()).isEqualTo(2);
    assertThat(((Map<String, Object>) objectArray.get(0)).get("index")).isEqualTo(0);
    assertThat(((Map<String, Object>) objectArray.get(1)).get("index")).isEqualTo(1);
  }

  @Test
  void shouldThrowExceptionForUnsupportedFormat() throws IOException {
    // Given
    FileData fileData =
        FileUtils.readFile(new File("target/test-data/inputs/simple/simple.properties"));

    // When & Then
    assertThrows(UnsupportedFileFormatException.class, () -> handler.handleFile(fileData));
  }
}
