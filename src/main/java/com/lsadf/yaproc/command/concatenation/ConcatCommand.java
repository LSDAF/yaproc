package com.lsadf.yaproc.command.concatenation;

import com.lsadf.yaproc.command.ACommand;
import com.lsadf.yaproc.command.YaprocCommand;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.util.FileHandlerUtils;
import com.lsadf.yaproc.util.FileUtils;
import com.lsadf.yaproc.util.ValidationUtils;
import java.io.File;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
    name = "concat",
    aliases = {"c", "concat"},
    description = "Concatenates multiple files of the same type into a single output file")
public class ConcatCommand extends ACommand<List<File>> implements YaprocCommand<List<File>> {

  @CommandLine.Parameters(arity = "2..*", description = "Command parameters list")
  private List<File> parameters;

  private List<File> input;
  private File output;

  @Override
  public Logger getLogger() {
    return log;
  }

  @Override
  public List<File> getInput() {
    return this.input;
  }

  @Override
  public File getOutput() {
    return this.output;
  }

  @Override
  public void init() {
    super.init();
    this.outputFileHandler = FileHandlerUtils.initOutputFileHandlers();
    this.output = parameters.get(0);
    this.input = parameters.subList(1, parameters.size());
  }

  @Override
  public Integer call() throws Exception {
    try {
      init();
      log.debug("Initializing concat command...");

      // Validate all input files have the same extension
      String commonExtension = validateFileExtensions();
      log.debug("Common file extension: " + commonExtension);

      // Validate file format is supported
      ValidationUtils.validateFileFormat(commonExtension);
      log.debug("File format validation successful");

      // Concatenate files
      ContentMap concatMap = new ContentMap();
      for (File file : input) {
        FileData fileData = FileUtils.readFile(file);
        ContentMap result = inputFileHandler.handleFile(fileData);
        concatMap.putAll(result);
      }

      // Write concatenated content to output file using the outputFileHandler
      outputFileHandler.handleFile(output, concatMap, isForce());
      log.debug("Successfully wrote concatenated content to: " + getOutput());

      return 0;
    } catch (Exception e) {
      log.error(e.getMessage());
      throw e;
    }
  }

  private String validateFileExtensions() {
    if (input.size() < 2) {
      throw new IllegalArgumentException("At least one input and one output file must be provided");
    }

    String outputExtension = FileUtils.getFileExtension(input.get(0));
    String inputExtension = FileUtils.getFileExtension(input.get(1));

    if (!outputExtension.equals(inputExtension)) {
      throw new IllegalArgumentException(
          String.format(
              "Output file and input file must have the same extension. Found different extensions: %s and %s",
              outputExtension, inputExtension));
    }

    for (int i = 2; i < input.size(); i++) {
      String currentExtension = FileUtils.getFileExtension(input.get(i));
      if (!inputExtension.equals(currentExtension)) {
        throw new IllegalArgumentException(
            String.format(
                "All files must have the same extension. Found different extensions: %s and %s",
                inputExtension, currentExtension));
      }
    }
    return inputExtension;
  }
}
