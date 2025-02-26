package com.lsadf.yaproc.file.handler.output;

import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.handler.FileHandler;

import java.io.IOException;

/**
 * This interface represents a handler for writing processed file content to an output file.
 * Classes implementing this interface should define how to handle creating or overwriting
 * output files based on the provided content.
 */
public interface OutputFileHandler extends FileHandler {

    /**
     * Handles writing the specified content to the given output file.
     *
     * @param outputFile the path to the output file where the content will be written
     * @param content    the content to be written to the output file
     * @param force      if {@code true}, forces overwriting the output file if it already exists
     * @throws IOException if an error occurs during file handling
     */
    void handleFile(String outputFile, ContentMap content, boolean force) throws IOException;

    /**
     * Sets the next handler in the chain of responsibility.
     * This allows the current handler to delegate the processing
     * of unsupported file types to the next handler.
     *
     * @param nextHandler the next {@link FileHandler} in the chain.
     */
    void setNextHandler(OutputFileHandler nextHandler);
}
