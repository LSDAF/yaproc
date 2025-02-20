package com.lsadf.yaproc.file.handler.output;

import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileFormat;

import java.io.IOException;

public interface OutputFileHandler {
    FileFormat getType();
    void handleFile(String outputFile, ContentMap contentMap, boolean force) throws IOException;
}
