package com.lsadf.yaproc.util;

import com.lsadf.yaproc.file.handler.input.InputFileHandler;
import com.lsadf.yaproc.file.handler.input.JsonInputFileHandler;
import com.lsadf.yaproc.file.handler.input.PropertiesInputFileHandler;
import com.lsadf.yaproc.file.handler.input.YamlInputFileHandler;
import com.lsadf.yaproc.file.handler.output.JsonOutputFileHandler;
import com.lsadf.yaproc.file.handler.output.OutputFileHandler;
import com.lsadf.yaproc.file.handler.output.YamlOutputFileHandler;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FileHandlerUtils {
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
