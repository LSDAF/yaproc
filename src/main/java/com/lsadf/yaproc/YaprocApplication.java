package com.lsadf.yaproc;

import com.lsadf.yaproc.command.concatenation.ConcatCommand;
import com.lsadf.yaproc.command.conversion.JsonCommand;
import com.lsadf.yaproc.command.conversion.PropertiesCommand;
import com.lsadf.yaproc.command.conversion.YamlCommand;
import picocli.CommandLine;

@CommandLine.Command(
    name = "yaproc",
    subcommands = {
            JsonCommand.class,
            PropertiesCommand.class,
            YamlCommand.class,
            ConcatCommand.class,
            CommandLine.HelpCommand.class})
public class YaprocApplication {
  public static void main(String[] args) {
    int exitCode = new CommandLine(new YaprocApplication())
            .setExecutionExceptionHandler(new YaprocExceptionHandler())
            .execute(args);
    System.exit(exitCode);
  }
}
