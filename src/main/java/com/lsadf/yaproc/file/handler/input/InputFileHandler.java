package com.lsadf.yaproc.file.handler.input;

import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.file.FileFormat;

import java.io.IOException;

public interface InputFileHandler {
    FileFormat getType();
    ContentMap handleFile(FileData fileData) throws IOException;
    void setNextHandler(InputFileHandler nextHandler);
}
