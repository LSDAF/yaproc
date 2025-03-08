package com.lsadf.yaproc.file.handler.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileFormat;
import com.lsadf.yaproc.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * A handler class responsible for generating YAML output files.
 * It converts the provided data into YAML format and writes it to the specified output file.
 */
public class YamlOutputFileHandler implements OutputFileHandler {
    private OutputFileHandler nextHandler;
    private final ObjectMapper yamlMapper;

    public YamlOutputFileHandler() {
        this.yamlMapper = new ObjectMapper(new YAMLFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileFormat getType() {
        return FileFormat.YAML;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFile(File outputFile, ContentMap contentMap, boolean force) throws IOException {

        String type = FileUtils.getFileExtension(outputFile);
        if (Arrays.stream(getType().getExtensions()).noneMatch(ext -> ext.equalsIgnoreCase(type))) {
            if (nextHandler != null) {
                nextHandler.handleFile(outputFile, contentMap, force);
            }
            return;
        }

        // Convert data to YAML
        String yamlContent = yamlMapper.writeValueAsString(contentMap);

        // Write YAML to file
        FileUtils.writeFile(outputFile, yamlContent, force);
    }

    @Override
    public void setNextHandler(OutputFileHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
