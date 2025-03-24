package com.lsadf.yaproc.command.conversion;

import com.lsadf.yaproc.command.YaprocCommand;
import com.lsadf.yaproc.file.handler.output.YamlOutputFileHandler;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
    name = "yaml",
    aliases = {"y", "yml"},
    description = "Converts the given input file to YAML")
public class YamlCommand extends AConversionCommand implements YaprocCommand<File> {

  @Override
  public void init() {
    super.init();
    this.outputFileHandler = new YamlOutputFileHandler();
  }

  @Override
  public Logger getLogger() {
    return log;
  }
}
