package com.lsadf.yaproc.file.handler.input;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.lsadf.yaproc.exception.UnsupportedFileFormatException;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.file.FileFormat;
import java.io.IOException;
import java.util.Arrays;

/**
 * A file handler that processes JSON input files.
 *
 * <p>The handler uses the {@link JsonMapper} to parse the content of JSON files into a {@link
 * ContentMap}. It supports file types as defined by {@link FileFormat#JSON}.
 *
 * <p>If the JSON file cannot be processed, the request can be passed to a subsequent handler in a
 * chain of responsibility. If no subsequent handler is set, an {@link
 * UnsupportedFileFormatException} will be thrown.
 */
public class JsonInputFileHandler implements InputFileHandler {

  private InputFileHandler nextHandler;
  private final JsonMapper jsonMapper;

  public JsonInputFileHandler() {
    this.jsonMapper = new JsonMapper();
  }

  /** {@inheritDoc} */
  @Override
  public FileFormat getType() {
    return FileFormat.JSON;
  }

  /** {@inheritDoc} */
  @Override
  public ContentMap handleFile(FileData fileData) throws IOException {
    String type = fileData.getType();
    if (Arrays.stream(getType().getExtensions()).anyMatch(ext -> ext.equalsIgnoreCase(type))) {
      return this.jsonMapper.readValue(fileData.getContent(), ContentMap.class);
    }
    if (nextHandler == null) {
      throw new UnsupportedFileFormatException("Unsupported file format.");
    }

    return nextHandler.handleFile(fileData);
  }

  @Override
  public void setNextHandler(InputFileHandler nextHandler) {
    this.nextHandler = nextHandler;
  }
}
