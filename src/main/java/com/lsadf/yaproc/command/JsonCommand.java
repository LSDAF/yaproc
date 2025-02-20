package com.lsadf.yaproc.command;

import com.lsadf.yaproc.file.handler.output.JsonOutputFileHandler;
import picocli.CommandLine;

@CommandLine.Command(
    name = "json",
    aliases = {"j", "json"},
    description = "Converts the given input file to JSON")
public class JsonCommand extends ACommand implements YaprocCommand {

  @Override
  public void init() {
    super.init();
    this.outputFileHandler = new JsonOutputFileHandler();
  }
}
