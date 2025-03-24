package com.lsadf.yaproc.command;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.lsadf.yaproc.file.handler.input.InputFileHandler;
import com.lsadf.yaproc.file.handler.output.OutputFileHandler;
import com.lsadf.yaproc.util.FileHandlerUtils;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

public abstract class ACommand<I> implements YaprocCommand<I> {
  @CommandLine.Spec protected CommandLine.Model.CommandSpec spec;

  @CommandLine.Option(
      names = {"-force", "-f"},
      description = "Overwrite output file if it already exists")
  protected boolean force;

  @CommandLine.Option(
      names = {"--debug", "-d"},
      description = "Show debug information")
  protected boolean debug;

  protected InputFileHandler inputFileHandler = FileHandlerUtils.initInputFileHandlers();
  protected OutputFileHandler outputFileHandler;

  @Override
  public CommandLine.Model.CommandSpec getSpec() {
    return this.spec;
  }

  @Override
  public boolean isForce() {
    return this.force;
  }

  @Override
  public boolean isDebug() {
    return this.debug;
  }

  @Override
  public OutputFileHandler getOutputFileHandler() {
    return this.outputFileHandler;
  }

  @Override
  public InputFileHandler getInputFileHandler() {
    return this.inputFileHandler;
  }

  @Override
  public void init() {
    if (debug) {
      Logger logger = (Logger) LoggerFactory.getLogger("com.lsadf.yaproc");
      logger.setLevel(Level.DEBUG);
      getLogger().info("Debug mode enabled");
    }
  }
}
