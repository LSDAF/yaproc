package com.lsadf.yaproc.file.handler;

import com.lsadf.yaproc.file.FileFormat;

/**
 * Interface representing a handler in a chain of responsibility pattern
 * for processing files. Each handler is responsible for handling specific
 * file types and can delegate to the next handler in the chain if needed.
 */
public interface FileHandler {

    /**
     * Gets the type of file format that this handler can process.
     *
     * @return a {@link FileFormat} representing the supported file type.
     */
    FileFormat getType();

}
