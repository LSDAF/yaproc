package com.lsadf.yaproc.file.handler.input;

import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.file.FileFormat;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Properties;

public class PropertiesInputFileHandler implements InputFileHandler {
    private InputFileHandler nextHandler;

    @Override
    public FileFormat getType() {
        return FileFormat.PROPERTIES;
    }

    @Override
    public ContentMap handleFile(FileData fileData) throws IOException {
        String type = fileData.getType();
        if (Arrays.stream(getType().getExtensions()).anyMatch(ext -> ext.equalsIgnoreCase(type))) {
            Properties props = new Properties();
            props.load(new StringReader(fileData.getContent()));
            
            ContentMap contentMap = new ContentMap();
            props.forEach((key, value) -> contentMap.put(key.toString(), value));
            return contentMap;
        } else if (nextHandler != null) {
            return nextHandler.handleFile(fileData);
        }
        return null;
    }

    @Override
    public void setNextHandler(InputFileHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
