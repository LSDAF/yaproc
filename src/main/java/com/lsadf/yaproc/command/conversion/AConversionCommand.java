package com.lsadf.yaproc.command.conversion;

import com.lsadf.yaproc.command.ACommand;
import com.lsadf.yaproc.command.YaprocCommand;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.util.FileUtils;
import com.lsadf.yaproc.util.ValidationUtils;
import picocli.CommandLine;

public abstract class AConversionCommand extends ACommand<String> implements YaprocCommand<String> {

  @CommandLine.Parameters(index = "0", description = "Input file")
  protected String input;

  @CommandLine.Parameters(index = "1", description = "Output file")
  protected String output;

  @Override
  public Integer call() throws Exception {
    try {
      init();
      if (verbose || debug) {
        getLogger().info("Initializing command...");
      }

      // get input extension
      String inputExtension = FileUtils.getFileExtension(getInput());
      if (debug) {
        getLogger().info("Input file extension: " + inputExtension);
      }

      // if input extension is not supported, throw an exception
      ValidationUtils.validateFileFormat(inputExtension);
      if (verbose || debug) {
        getLogger().info("File format validation successful");
      }

      FileData fileData = FileUtils.readFile(input);
      if (debug) {
        getLogger().info("Read input file: " + input);
        getLogger().info("File content length: " + fileData.getContent().length());
      }

      ContentMap fileContent = inputFileHandler.handleFile(fileData);
      if (debug) {
        getLogger().info("Processed input file. Content map size: " + fileContent.size());
      }

      outputFileHandler.handleFile(output, fileContent, isForce());
      if (verbose || debug) {
        getLogger().info("Successfully wrote output to: " + output);
      }

      return 0;
    } catch (Exception e) {
      if (debug) {
        getLogger().severe(e.getMessage());
      } else if (verbose) {
        getLogger().severe("Error: " + e.getMessage());
      }
      throw e;
    }
  }

  @Override
  public String getInput() {
    return input;
  }

  @Override
  public String getOutput() {
    return this.output;
  }
}
