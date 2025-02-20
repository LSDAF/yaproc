package com.lsadf.yaproc.file.handler.input;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.lsadf.yaproc.exception.UnsupportedFileFormatException;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.file.FileFormat;

import java.io.IOException;
import java.util.Arrays;

public class JsonInputFileHandler implements InputFileHandler {

  private final JsonMapper jsonMapper;
  private InputFileHandler next;

  public JsonInputFileHandler() {
    this.jsonMapper = new JsonMapper();
  }

  @Override
  public FileFormat getType() {
    return FileFormat.JSON;
  }

  @Override
  public ContentMap handleFile(FileData fileData) throws IOException {
    if (Arrays.stream(getType().getExtensions()).anyMatch(ext -> ext.equalsIgnoreCase(fileData.getType()))) {
      return this.jsonMapper.readValue(fileData.getContent(), ContentMap.class);
    }
    if (next == null) {
      throw new UnsupportedFileFormatException("Unsupported file format.");
    }

    return next.handleFile(fileData);
  }

  @Override
  public void setNextHandler(InputFileHandler nextHandler) {
    this.next = nextHandler;
  }
}
