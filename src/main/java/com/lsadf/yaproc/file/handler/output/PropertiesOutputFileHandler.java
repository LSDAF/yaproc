package com.lsadf.yaproc.file.handler.output;

import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileFormat;
import com.lsadf.yaproc.util.FileUtils;

import java.io.IOException;
import java.util.Arrays;

public class PropertiesOutputFileHandler implements OutputFileHandler {

    private OutputFileHandler nextHandler;

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFile(String outputFile, ContentMap contentMap, boolean force) throws IOException {
        // TODO

        String type = FileUtils.getFileExtension(outputFile);
        if (Arrays.stream(getType().getExtensions()).noneMatch(ext -> ext.equalsIgnoreCase(type))) {
            if (nextHandler != null) {
                nextHandler.handleFile(outputFile, contentMap, force);
            }
        }
    }

    @Override
    public void setNextHandler(OutputFileHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileFormat getType() {
        return FileFormat.PROPERTIES;
    }
}
