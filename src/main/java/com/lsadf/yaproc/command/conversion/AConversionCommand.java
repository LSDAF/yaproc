package com.lsadf.yaproc.command.conversion;

import com.lsadf.yaproc.command.ACommand;
import com.lsadf.yaproc.command.YaprocCommand;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.util.FileUtils;
import com.lsadf.yaproc.util.ValidationUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import picocli.CommandLine;

public abstract class AConversionCommand extends ACommand<File> implements YaprocCommand<File> {

  @CommandLine.Parameters(arity = "2", description = "Command parameters list")
  protected List<File> parameters;

  protected File input;
  protected File output;

  @Override
  public Integer call() throws Exception {
    try {
      init();
      getLogger().debug("Initializing command...");

      // Check if input exists
      if (!input.exists()) {
        throw new FileNotFoundException("Input file does not exist: " + input);
      }

      // get input extension
      String inputExtension = FileUtils.getFileExtension(input);
      getLogger().debug("Input file extension: {}", inputExtension);

      // if input extension is not supported, throw an exception
      ValidationUtils.validateFileFormat(inputExtension);
      getLogger().debug("File format validation successful");

      FileData fileData = FileUtils.readFile(input);
      getLogger().debug("Read input file: {}", input);
      getLogger().debug("File content length: {}", fileData.getContent().length());

      ContentMap fileContent = inputFileHandler.handleFile(fileData);
      getLogger().debug("Processed input file. Content map size: {}", fileContent.size());

      outputFileHandler.handleFile(output, fileContent, isForce());
      getLogger().debug("Successfully wrote output to: {}", output);

      return 0;
    } catch (Exception e) {
      getLogger().error(e.getMessage());
      throw e;
    }
  }

  @Override
  public File getInput() {
    return input;
  }

  @Override
  public File getOutput() {
    return output;
  }

  @Override
  public void init() {
    super.init();
    this.input = parameters.get(0);
    this.output = parameters.get(1);
  }
}
