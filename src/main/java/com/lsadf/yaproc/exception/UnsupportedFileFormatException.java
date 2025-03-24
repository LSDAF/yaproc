package com.lsadf.yaproc.exception;

/**
 * Exception thrown to indicate that a file format is unsupported by the system or the associated
 * file handler.
 *
 * <p>This exception is typically used when attempting to process a file using a specific handler,
 * but the file's format is invalid or unrecognized by that handler. For example, this exception can
 * arise when attempting to handle a file format other than the expected types (e.g., JSON, YAML).
 *
 * <p>It extends {@code RuntimeException}, allowing it to represent runtime errors without requiring
 * explicit throws declarations in method signatures.
 *
 * <p>The exception includes a message that provides additional details about the unsupported file
 * format to aid in debugging or error reporting.
 *
 * <p>Usage of this class is commonly associated with custom file handlers validating input against
 * specified formats.
 */
public class UnsupportedFileFormatException extends RuntimeException {
  public UnsupportedFileFormatException(String message) {
    super(message);
  }
}
