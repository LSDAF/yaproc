package com.lsadf.yaproc.file.handler.input;

import com.lsadf.yaproc.exception.UnsupportedFileFormatException;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.file.FileFormat;
import com.lsadf.yaproc.util.ClassUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Properties;

/**
 * Handles input files with the PROPERTIES format.
 * The class processes property files and converts them into a {@link ContentMap}.
 * If the file format is not supported, it delegates to the next handler in the chain.
 */
public class PropertiesInputFileHandler implements InputFileHandler {
    private InputFileHandler nextHandler;

    /**
     * {@inheritDoc}
     */
    @Override
    public FileFormat getType() {
        return FileFormat.PROPERTIES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContentMap handleFile(FileData fileData) throws IOException {
        String type = fileData.getType();
        if (Arrays.stream(getType().getExtensions()).anyMatch(ext -> ext.equalsIgnoreCase(type))) {
            Properties props = new Properties();
            props.load(new StringReader(fileData.getContent()));

            ContentMap contentMap = new ContentMap();
            props.forEach((key, value) -> {
                if (ClassUtils.isNumber(value.toString())) {
                    contentMap.put(key.toString(), Long.parseLong(value.toString()));
                } else if (ClassUtils.isBoolean(value.toString())) {
                    contentMap.put(key.toString(), Boolean.parseBoolean(value.toString()));
                } else {
                    contentMap.put(key.toString(), value);
                }
            });

            return contentMap;
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
