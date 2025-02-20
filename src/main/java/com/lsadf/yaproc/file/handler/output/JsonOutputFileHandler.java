package com.lsadf.yaproc.file.handler.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileFormat;
import com.lsadf.yaproc.util.FileUtils;

import java.io.IOException;

public class JsonOutputFileHandler implements OutputFileHandler {

    private final ObjectMapper jsonMapper;

    public JsonOutputFileHandler() {
        this.jsonMapper = new ObjectMapper();
    }

    @Override
    public FileFormat getType() {
        return FileFormat.JSON;
    }

    @Override
    public void handleFile(String outputFile, ContentMap contentMap, boolean force) throws IOException {
        // Convert data to JSON
        String jsonContent = jsonMapper.writeValueAsString(contentMap);

        // Write JSON to file
        FileUtils.writeFile(outputFile, jsonContent, force);
    }
}
