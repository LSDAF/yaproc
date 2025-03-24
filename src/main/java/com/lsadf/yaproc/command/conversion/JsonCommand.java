package com.lsadf.yaproc.command.conversion;

import com.lsadf.yaproc.command.YaprocCommand;
import com.lsadf.yaproc.file.handler.output.JsonOutputFileHandler;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
    name = "json",
    aliases = {"j", "json"},
    description = "Converts the given input file to JSON")
public class JsonCommand extends AConversionCommand implements YaprocCommand<File> {

  @Override
  public void init() {
    super.init();
    this.outputFileHandler = new JsonOutputFileHandler();
  }

  @Override
  public Logger getLogger() {
    return log;
  }
}
