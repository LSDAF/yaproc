package com.lsadf.yaproc.command;

import picocli.CommandLine;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * Interface representing a command in the Yaproc application.
 * All commands must implement this interface to ensure consistency.
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
  String getOutput();

  /**
   * Indicates whether the force option is enabled.
   *
   * @return {@code true} if the operation should overwrite existing content, {@code false} otherwise
   */
  boolean isForce();

  /**
   * Indicates whether verbose logging is enabled.
   *
   * @return {@code true} if verbose mode is active, {@code false} otherwise
   */
  boolean isVerbose();

  /**
   * Indicates whether debugging mode is enabled.
   *
   * @return {@code true} if debug mode is active, {@code false} otherwise
   */
  boolean isDebug();

  /**
   * Initializes the command, setting up any required resources or configurations.
   */
  void init();
}
