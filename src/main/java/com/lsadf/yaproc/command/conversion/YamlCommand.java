package com.lsadf.yaproc.command.conversion;

import com.lsadf.yaproc.command.YaprocCommand;
import com.lsadf.yaproc.file.handler.output.YamlOutputFileHandler;
import picocli.CommandLine;

import java.util.logging.Logger;

@CommandLine.Command(
    name = "yaml",
    aliases = {"y", "yml"},
    description = "Converts the given input file to YAML")
public class YamlCommand extends AConversionCommand implements YaprocCommand<String> {

    private static final Logger LOGGER = Logger.getLogger(YamlCommand.class.getName());

    @Override
    public void init() {
        this.outputFileHandler = new YamlOutputFileHandler();
    }

    @Override
    public CommandLine.Model.CommandSpec getSpec() {
        return this.spec;
    }


    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}
