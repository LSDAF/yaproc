package com.lsadf.yaproc.command;

import com.lsadf.yaproc.file.handler.input.InputFileHandler;
import com.lsadf.yaproc.file.handler.output.OutputFileHandler;
import com.lsadf.yaproc.util.FileHandlerUtils;
import picocli.CommandLine;

public abstract class ACommand<I> implements YaprocCommand<I> {
    @CommandLine.Spec protected CommandLine.Model.CommandSpec spec;

    @CommandLine.Option(
            names = {"-force", "-f"},
            description = "Overwrite output file if it already exists")
    protected boolean force;

    @CommandLine.Option(names = {"--verbose", "-v"}, description = "Show more detailed output")
    protected boolean verbose;

    @CommandLine.Option(names = {"--debug", "-d"}, description = "Show debug information")
    protected boolean debug;

    protected InputFileHandler inputFileHandler = FileHandlerUtils.initInputFileHandlers();
    protected OutputFileHandler outputFileHandler;

    @Override
    public CommandLine.Model.CommandSpec getSpec() {
        return this.spec;
    }

    @Override
    public boolean isForce() {
        return this.force;
    }

    @Override
    public boolean isDebug() {
        return this.debug;
    }

    @Override
    public boolean isVerbose() {
        return this.verbose;
    }
}
