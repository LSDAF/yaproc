package com.lsadf.yaproc.file.handler.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileFormat;
import com.lsadf.yaproc.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class JsonOutputFileHandler implements OutputFileHandler {
  private OutputFileHandler nextHandler;
  private final ObjectMapper jsonMapper;

  public JsonOutputFileHandler() {
    this.jsonMapper = new ObjectMapper();
  }

  @Override
  public FileFormat getType() {
    return FileFormat.JSON;
  }

  @Override
  public void handleFile(File outputFile, ContentMap contentMap, boolean force) throws IOException {

    String type = FileUtils.getFileExtension(outputFile);
    if (Arrays.stream(getType().getExtensions()).noneMatch(ext -> ext.equalsIgnoreCase(type))) {
      if (nextHandler != null) {
        nextHandler.handleFile(outputFile, contentMap, force);
      }
      return;
    }

    // Convert data to JSON
    String jsonContent = jsonMapper.writeValueAsString(contentMap);

    // Write JSON to file
    FileUtils.writeFile(outputFile, jsonContent, force);
  }

  @Override
  public void setNextHandler(OutputFileHandler nextHandler) {
    this.nextHandler = nextHandler;
  }
}
