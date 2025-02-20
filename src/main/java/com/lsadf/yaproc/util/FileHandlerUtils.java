package com.lsadf.yaproc.util;

import com.lsadf.yaproc.file.handler.input.InputFileHandler;
import com.lsadf.yaproc.file.handler.input.JsonInputFileHandler;
import com.lsadf.yaproc.file.handler.input.PropertiesInputFileHandler;
import com.lsadf.yaproc.file.handler.input.YamlInputFileHandler;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FileHandlerUtils {
    public static InputFileHandler initFileHandlers() {
        // Add all file handlers here
        InputFileHandler jsonHandler = new JsonInputFileHandler();
        InputFileHandler yamlHandler = new YamlInputFileHandler();
        InputFileHandler propertiesHandler = new PropertiesInputFileHandler();

        // Connect the handlers, for first to last
        jsonHandler.setNextHandler(yamlHandler);
        yamlHandler.setNextHandler(propertiesHandler);

        // Return the first handler
        return jsonHandler;
    }
}
