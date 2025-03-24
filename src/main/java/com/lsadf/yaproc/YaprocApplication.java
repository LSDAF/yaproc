package com.lsadf.yaproc;

import com.lsadf.yaproc.command.VersionCommand;
import com.lsadf.yaproc.command.concatenation.ConcatCommand;
import com.lsadf.yaproc.command.conversion.JsonCommand;
import com.lsadf.yaproc.command.conversion.PropertiesCommand;
import com.lsadf.yaproc.command.conversion.YamlCommand;
import picocli.CommandLine;

/**
 * The YaprocApplication serves as the entry point for the YAPROC command-line utility. It utilizes
 * the PicoCLI library to register and execute subcommands for converting and manipulating file
 * formats like JSON, YAML, and properties files. Additionally, the application allows concatenation
 * of multiple files of the same type.
 *
 * <p>Subcommands: - JsonCommand: Converts input files to JSON format. - PropertiesCommand: Converts
 * input files to properties format. - YamlCommand: Converts input files to YAML format. -
 * ConcatCommand: Concatenates multiple files of the same type. - CommandLine.HelpCommand: Provides
 * help information for supported commands.
 *
 * <p>The application sets a custom execution exception handler to handle specific exceptions, such
 * as file not found or unsupported file formats, and exits with appropriate status codes.
 */
@CommandLine.Command(
    name = "yaproc",
    footer = "lsadf - 2025",
    subcommands = {
      JsonCommand.class,
      PropertiesCommand.class,
      YamlCommand.class,
      ConcatCommand.class,
      CommandLine.HelpCommand.class,
      VersionCommand.class
    })
public class YaprocApplication {

  /**
   * The main entry point for the Yaproc application. This method uses the PicoCLI framework to
   * parse and execute command-line arguments, while handling any execution exceptions with a custom
   * exception handler. After processing, it exits with an appropriate status code.
   *
   * @param args the command-line arguments passed to the application
   */
  public static void main(String[] args) {
    int exitCode =
        new CommandLine(new YaprocApplication())
            .setExecutionExceptionHandler(new YaprocExceptionHandler())
            .execute(args);
    System.exit(exitCode);
  }
}
