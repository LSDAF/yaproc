package com.lsadf.yaproc.util;

import com.lsadf.yaproc.file.handler.input.InputFileHandler;
import com.lsadf.yaproc.file.handler.input.JsonInputFileHandler;
import com.lsadf.yaproc.file.handler.input.PropertiesInputFileHandler;
import com.lsadf.yaproc.file.handler.input.YamlInputFileHandler;
import com.lsadf.yaproc.file.handler.output.JsonOutputFileHandler;
import com.lsadf.yaproc.file.handler.output.OutputFileHandler;
import com.lsadf.yaproc.file.handler.output.YamlOutputFileHandler;
import lombok.experimental.UtilityClass;

/**
 * Utility class for initializing and managing chains of file handlers for input and output
 * operations. This class simplifies the creation of chains of responsibility for processing
 * different file types.
 */
@UtilityClass
public class FileHandlerUtils {
  /**
   * Initializes a chain of input file handlers for processing different file formats. The chain
   * includes handlers for JSON, YAML, and PROPERTIES file formats, set up in the specified order.
   * Each handler is responsible for processing its designated file type and delegating unsupported
   * file types to the next handler in the chain.
   *
   * @return the first {@link InputFileHandler} in
   */
  public static InputFileHandler initInputFileHandlers() {
    // Add all file handlers here
    InputFileHandler jsonHandler = new JsonInputFileHandler();
    InputFileHandler yamlHandler = new YamlInputFileHandler();
    InputFileHandler propertiesHandler = new PropertiesInputFileHandler();

    // Connect the handlers, from first to last
    jsonHandler.setNextHandler(yamlHandler);
    yamlHandler.setNextHandler(propertiesHandler);

    // Return the first handler
    return jsonHandler;
  }

  /**
   * Initializes a chain of {@link OutputFileHandler} instances for processing different output file
   * formats. The chain includes handlers for JSON, YAML, and Properties file formats, set up in the
   * specified order. Each handler is responsible for processing its designated file type and
   * delegating unsupported file types to the next handler in the chain.
   *
   * @return the first {@link OutputFileHandler} in the chain, which serves as the entry point for
   *     handling output file operations.
   */
  public static OutputFileHandler initOutputFileHandlers() {
    // Add all file handlers here
    OutputFileHandler jsonHandler = new JsonOutputFileHandler();
    OutputFileHandler yamlHandler = new YamlOutputFileHandler();
    OutputFileHandler propertiesHandler = new YamlOutputFileHandler();

    // Connect the handlers from first to last
    jsonHandler.setNextHandler(yamlHandler);
    yamlHandler.setNextHandler(propertiesHandler);

    return jsonHandler;
  }
}
