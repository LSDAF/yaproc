package com.lsadf.yaproc;

import com.lsadf.yaproc.exception.UnsupportedFileFormatException;
import picocli.CommandLine;

/**
 * A custom exception handler used in the YaprocApplication to process and handle various exceptions
 * during command execution.
 *
 * <p>This handler is integrated with the PicoCLI framework's exception handling mechanism and is
 * designed to map specific exceptions to application-specific exit codes. It handles the following
 * exceptions:
 *
 * <p>1. {@code java.io.FileNotFoundException}: Indicates a file was not found during execution.
 * Returns the exit code {@code CommandLine.ExitCode.SOFTWARE}. 2. {@code
 * UnsupportedFileFormatException}: Indicates that a file's format is unsupported by the
 * application. Returns a custom exit code, {@code 3}. 3. All other exceptions: Default to returning
 * the exit code {@code CommandLine.ExitCode.SOFTWARE}.
 *
 * <p>This class is typically used to ensure that the application exits with consistent and
 * meaningful status codes based on the type of exception encountered, aiding in debugging or error
 * resolution when executing commands.
 */
public class YaprocExceptionHandler implements CommandLine.IExecutionExceptionHandler {

  /**
   * Handles exceptions that occur during the execution of a command and maps them to appropriate
   * exit codes.
   *
   * <p>This method is an implementation of the PicoCLI framework's {@code
   * IExecutionExceptionHandler} interface. It processes specific exceptions, determining the
   * corresponding exit code to return to the application. For unknown exceptions, it defaults to
   * returning the {@code SOFTWARE} exit code.
   *
   * @param e the exception thrown during command execution
   * @param commandLine the command line object associated with the execution
   * @param parseResult the results of parsing the command-line arguments
   * @return the integer exit code associated with the exception
   * @throws Exception if the exception needs to be rethrown
   */
  @Override
  public int handleExecutionException(
      Exception e, CommandLine commandLine, CommandLine.ParseResult parseResult) throws Exception {
    if (e instanceof java.io.FileNotFoundException) {
      return CommandLine.ExitCode.SOFTWARE;
    } else if (e instanceof UnsupportedFileFormatException) {
      return 3;
    }
    return CommandLine.ExitCode.SOFTWARE;
  }
}
