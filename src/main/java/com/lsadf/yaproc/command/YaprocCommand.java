package com.lsadf.yaproc.command;

import com.lsadf.yaproc.file.handler.input.InputFileHandler;
import com.lsadf.yaproc.file.handler.output.OutputFileHandler;
import java.io.File;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import picocli.CommandLine;

/**
 * Interface representing a command in the Yaproc application. All commands must implement this
 * interface to ensure consistency.
 */
public interface YaprocCommand<I> extends Callable<Integer> {

  /**
   * Gets the PicoCLI command specification associated with this command.
   *
   * @return the {@link CommandLine.Model.CommandSpec} of the command
   */
  CommandLine.Model.CommandSpec getSpec();

  /**
   * Gets the logger used for logging in the command.
   *
   * @return a {@link Logger} instance for the command
   */
  Logger getLogger();

  /**
   * Gets the input data or path required by this command.
   *
   * @return a {@link String} representing the input
   */
  I getInput();

  /**
   * Gets the output path where the command will store its result.
   *
   * @return a {@link String} representing the output path
   */
  File getOutput();

  /**
   * Indicates whether the force option is enabled.
   *
   * @return {@code true} if the operation should overwrite existing content, {@code false}
   *     otherwise
   */
  boolean isForce();

  /**
   * Indicates whether debugging mode is enabled.
   *
   * @return {@code true} if debug mode is active, {@code false} otherwise
   */
  boolean isDebug();

  /** Initializes the command, setting up any required resources or configurations. */
  void init();

  /**
   * Gets the input file handler associated with this command. The input file handler is responsible
   * for processing input files and converting them into a usable format.
   *
   * @return an {@link InputFileHandler} instance specific to the command
   */
  InputFileHandler getInputFileHandler();

  /**
   * Gets the output file handler associated with this command. The output file handler is
   * responsible for generating and writing the processed output file from the command execution.
   *
   * @return an {@link OutputFileHandler} instance specific to the command
   */
  OutputFileHandler getOutputFileHandler();
}
