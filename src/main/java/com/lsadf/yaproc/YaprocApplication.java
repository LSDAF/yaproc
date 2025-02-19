package com.lsadf.yaproc;

import com.lsadf.yaproc.command.JsonCommand;
import com.lsadf.yaproc.command.PropertiesCommand;
import com.lsadf.yaproc.command.YamlCommand;
import picocli.CommandLine;

@CommandLine.Command(
    name = "yaproc",
    subcommands = {JsonCommand.class, PropertiesCommand.class, YamlCommand.class, CommandLine.HelpCommand.class})
public class YaprocApplication {
  public static void main(String[] args) {
    int exitCode = new CommandLine(new YaprocApplication())
            .setExecutionExceptionHandler(new YaprocExceptionHandler())
            .execute(args);
    System.exit(exitCode);
  }
}
