package com.lsadf.yaproc.file.handler.input;

import com.lsadf.yaproc.exception.UnsupportedFileFormatException;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.file.FileFormat;

import java.io.IOException;
import java.util.Arrays;

public class YamlInputFileHandler implements InputFileHandler {
    private InputFileHandler next;
    @Override
    public FileFormat getType() {
        return FileFormat.YAML;
    }

    @Override
    public ContentMap handleFile(FileData fileData) throws IOException {
        String type = fileData.getType();
        if (Arrays.stream(getType().getExtensions()).anyMatch(ext -> ext.equalsIgnoreCase(type))) {
            System.out.println("YAML file");
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
