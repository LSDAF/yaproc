package com.lsadf.yaproc.file.handler.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileFormat;
import com.lsadf.yaproc.util.FileUtils;

import java.io.IOException;

public class YamlOutputFileHandler implements OutputFileHandler {

    private final ObjectMapper yamlMapper;

    public YamlOutputFileHandler() {
        this.yamlMapper = new ObjectMapper(new YAMLFactory());
    }

    @Override
    public FileFormat getType() {
        return FileFormat.YAML;
    }

    @Override
    public void handleFile(String outputFile, ContentMap contentMap, boolean force) throws IOException {
        // Convert data to YAML
        String yamlContent = yamlMapper.writeValueAsString(contentMap);

        // Write YAML to file
        FileUtils.writeFile(outputFile, yamlContent, force);
    }
}
