package com.lsadf.yaproc.command;

import com.lsadf.yaproc.file.handler.output.YamlOutputFileHandler;
import picocli.CommandLine;

@CommandLine.Command(
    name = "yaml",
    aliases = {"y", "yml"},
    description = "Converts the given input file to YAML")
public class YamlCommand extends ACommand {

    @CommandLine.Spec 
    private CommandLine.Model.CommandSpec spec;

    @CommandLine.Parameters(index = "0", description = "Input file")
    private String inputFile;

    @CommandLine.Parameters(index = "1", description = "Output YAML file")
    private String outputFile;

    @Override
    public void init() {
        super.init();
        this.outputFileHandler = new YamlOutputFileHandler();
    }

    @Override
    public CommandLine.Model.CommandSpec getSpec() {
        return this.spec;
    }

    @Override
    public String getInput() {
        return this.inputFile;
    }

    @Override
    public String getOutput() {
        return this.outputFile;
    }
}
