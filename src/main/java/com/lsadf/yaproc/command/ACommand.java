package com.lsadf.yaproc.command;

import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.file.handler.input.InputFileHandler;
import com.lsadf.yaproc.file.handler.output.OutputFileHandler;
import com.lsadf.yaproc.util.FileHandlerUtils;
import com.lsadf.yaproc.util.FileUtils;
import com.lsadf.yaproc.util.ValidationUtils;
import picocli.CommandLine;

public abstract class ACommand implements YaprocCommand {

  @CommandLine.Spec private CommandLine.Model.CommandSpec spec;

  @CommandLine.Parameters(index = "0", description = "Input file")
  protected String input;

  @CommandLine.Parameters(index = "1", description = "Output JSON file")
  protected String output;

  @CommandLine.Option(
      names = {"-force", "-f"},
      description = "Overwrite output file if it already exists")
  protected boolean force;

  @CommandLine.Option(names = {"--verbose", "-v"}, description = "Show more detailed output")
  protected boolean verbose;

  @CommandLine.Option(names = {"--debug", "-d"}, description = "Show debug information")
  protected boolean debug;

  protected InputFileHandler inputFileHandler;
  protected OutputFileHandler outputFileHandler;

  @Override
  public void init() {
    this.inputFileHandler = FileHandlerUtils.initFileHandlers();
  }

  @Override
  public Integer call() throws Exception {
    try {
      init();
      if (verbose || debug) {
        System.out.println("Initializing command...");
      }

      // get input extension
      String inputExtension = FileUtils.getFileExtension(getInput());
      if (debug) {
        System.out.println("Input file extension: " + inputExtension);
      }

      // if input extension is not supported, throw an exception
      ValidationUtils.validateFileFormat(inputExtension);
      if (verbose || debug) {
        System.out.println("File format validation successful");
      }

      FileData fileData = FileUtils.readFile(getInput());
      if (debug) {
        System.out.println("Read input file: " + getInput());
        System.out.println("File content length: " + fileData.getContent().length());
      }

      ContentMap fileContent = inputFileHandler.handleFile(fileData);
      if (debug) {
        System.out.println("Processed input file. Content map size: " + fileContent.size());
      }

      outputFileHandler.handleFile(getOutput(), fileContent, isForce());
      if (verbose || debug) {
        System.out.println("Successfully wrote output to: " + getOutput());
      }

      return 0;
    } catch (Exception e) {
      if (debug) {
        e.printStackTrace();
      } else if (verbose) {
        System.err.println("Error: " + e.getMessage());
      }
      throw e;
    }
  }

  @Override
  public CommandLine.Model.CommandSpec getSpec() {
    return this.spec;
  }

  @Override
  public String getInput() {
    return this.input;
  }

  @Override
  public String getOutput() {
    return this.output;
  }

  @Override
  public boolean isForce() {
    return this.force;
  }
}
