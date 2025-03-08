package com.lsadf.yaproc.command.conversion;

import com.lsadf.yaproc.command.YaprocCommand;
import com.lsadf.yaproc.file.handler.output.JsonOutputFileHandler;
import picocli.CommandLine;

import java.io.File;
import java.util.logging.Logger;

@CommandLine.Command(
    name = "json",
    aliases = {"j", "json"},
    description = "Converts the given input file to JSON")
public class JsonCommand extends AConversionCommand implements YaprocCommand<File> {

  private static final Logger LOGGER = Logger.getLogger(JsonCommand.class.getName());

  @Override
  public void init() {
    super.init();
    this.outputFileHandler = new JsonOutputFileHandler();
  }

  @Override
  public Logger getLogger() {
    return LOGGER;
  }
}
