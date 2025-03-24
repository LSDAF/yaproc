package com.lsadf.yaproc.file.handler.input;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.lsadf.yaproc.exception.UnsupportedFileFormatException;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.file.FileFormat;
import java.io.IOException;
import java.util.Arrays;

/**
 * A handler for processing input files in YAML format. This class uses {@link YAMLMapper} to parse
 * the YAML content into a {@link ContentMap} object. It supports chaining multiple handlers where
 * unsupported file formats can be delegated to the next handler in the chain. If no handler exists,
 * an {@link UnsupportedFileFormatException} is thrown when processing unsupported formats.
 */
public class YamlInputFileHandler implements InputFileHandler {
  private final YAMLMapper yamlMapper;
  private InputFileHandler next;

  public YamlInputFileHandler() {
    this.yamlMapper = new YAMLMapper();
  }

  /** {@inheritDoc} */
  @Override
  public FileFormat getType() {
    return FileFormat.YAML;
  }

  /** {@inheritDoc} */
  @Override
  public ContentMap handleFile(FileData fileData) throws IOException {
    String type = fileData.getType();
    if (Arrays.stream(getType().getExtensions()).anyMatch(ext -> ext.equalsIgnoreCase(type))) {
      return this.yamlMapper.readValue(fileData.getContent(), ContentMap.class);
    }
    if (next == null) {
      throw new UnsupportedFileFormatException("Unsupported file format.");
    }
    return next.handleFile(fileData);
  }

  /** {@inheritDoc} */
  @Override
  public void setNextHandler(InputFileHandler nextHandler) {
    this.next = nextHandler;
  }
}
