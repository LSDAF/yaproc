package com.lsadf.yaproc.file.handler.input;

import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.file.handler.FileHandler;

import java.io.IOException;

/**
 * Interface for handling input file processing in the YAPROC application.
 * Extends the base FileHandler interface to provide specific input processing capabilities.
 * Implementations of this interface form a chain of responsibility for processing
 * different file formats.
 */
public interface InputFileHandler extends FileHandler {
    /**
     * Processes the input file data and converts it to a ContentMap.
     *
     * @param fileData The file data containing content and metadata to be processed
     * @return ContentMap containing the processed key-value pairs from the input
     * @throws IOException If there's an error reading or processing the file
     */
    ContentMap handleFile(FileData fileData) throws IOException;

    /**
     * Sets the next handler in the chain of responsibility.
     * This allows the current handler to delegate the processing
     * of unsupported file types to the next handler.
     *
     * @param nextHandler the next {@link FileHandler} in the chain.
     */
    void setNextHandler(InputFileHandler nextHandler);
}
