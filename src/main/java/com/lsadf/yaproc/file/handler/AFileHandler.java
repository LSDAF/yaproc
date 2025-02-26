package com.lsadf.yaproc.file.handler;

/**
 * Abstract class representing a file handler in a chain-of-responsibility pattern.
 * <p>
 * This class implements the {@link FileHandler} interface, providing a structure
 * for delegating file processing to the next handler in the chain if the current
 * handler cannot process it. Subclasses are expected to provide specific
 * file-handling logic.
 * </p>
 */
public abstract class AFileHandler implements FileHandler {
    protected FileHandler nextHandler;

    @Override
    public void setNextHandler(FileHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
