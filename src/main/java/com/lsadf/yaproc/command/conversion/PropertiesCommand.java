package com.lsadf.yaproc.command.conversion;

import com.lsadf.yaproc.command.YaprocCommand;
import com.lsadf.yaproc.file.handler.output.PropertiesOutputFileHandler;
import picocli.CommandLine;

import java.io.File;
import java.util.logging.Logger;

@CommandLine.Command(
        name = "properties",
        aliases = {"p", "prop"},
        description = "Converts the given input file to properties")
public class PropertiesCommand extends AConversionCommand implements YaprocCommand<File> {

    private static final Logger LOGGER = Logger.getLogger(PropertiesCommand.class.getName());

    @Override
    public void init() {
        super.init();
        this.outputFileHandler = new PropertiesOutputFileHandler();
    }

    @Override
    public File getOutput() {
        return output;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}
