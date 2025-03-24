package com.lsadf.yaproc.test.file.handler.input;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.lsadf.yaproc.exception.UnsupportedFileFormatException;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.file.handler.input.InputFileHandler;
import com.lsadf.yaproc.file.handler.input.JsonInputFileHandler;
import com.lsadf.yaproc.util.FileUtils;
import java.io.File;
import java.io.IOException;
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
    assertThat(contentMap).isNotNull().isNotEmpty().containsEntry("key", "value");
  }

  @Test
  void shouldHandleComplexJsonFile() throws IOException, UnsupportedFileFormatException {
    // Given
    FileData fileData =
        FileUtils.readFile(new File("target/test-data/inputs/complex/complex.json"));

    // When
    ContentMap contentMap = handler.handleFile(fileData);

    // Then
    assertThat(contentMap).isNotNull().isNotEmpty();

    // Test object properties
    Map<String, Object> nestedObject = (Map<String, Object>) contentMap.get("nested_object");

    String anotherProperty = (String) nestedObject.get("another_property");
    assertThat(anotherProperty).isEqualTo("Test");

    List<Object> objectArray = (List<Object>) nestedObject.get("object_array");
    assertThat(objectArray).hasSize(2);

    Map<String, Object> firstObject = (Map<String, Object>) objectArray.get(0);
    assertThat(firstObject).hasSize(1).containsEntry("index", 0);

    Map<String, Object> secondObject = (Map<String, Object>) objectArray.get(1);
    assertThat(secondObject)
        .containsEntry("index", 1)
        .containsEntry("another_final_property", "TEST");
  }

  @Test
  void shouldThrowExceptionForUnsupportedFormat() throws IOException {
    // Given
    FileData fileData =
        FileUtils.readFile(new File("target/test-data/inputs/simple/simple.properties"));

    // When & Then
    assertThrows(UnsupportedFileFormatException.class, () -> handler.handleFile(fileData));
  }

  @Test
  void shouldThrowExceptionForInvalidJsonContent() throws IOException {
    // Given
    FileData fileData =
        FileUtils.readFile(new File("target/test-data/inputs/malformed/malformed.json"));

    // When & Then
    assertThrows(IOException.class, () -> handler.handleFile(fileData));
  }
}
