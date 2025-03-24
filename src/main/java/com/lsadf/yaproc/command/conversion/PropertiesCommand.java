package com.lsadf.yaproc.command.conversion;

import com.lsadf.yaproc.command.YaprocCommand;
import com.lsadf.yaproc.file.handler.output.PropertiesOutputFileHandler;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
    name = "properties",
    aliases = {"p", "prop"},
    description = "Converts the given input file to properties")
public class PropertiesCommand extends AConversionCommand implements YaprocCommand<File> {

  @Override
  public void init() {
    super.init();
    this.outputFileHandler = new PropertiesOutputFileHandler();
  }

  @Override
  public File getOutput() {
    return output;
  }

  @Override
  public Logger getLogger() {
    return log;
  }
}
