package com.lsadf.yaproc.command.concatenation;

import com.lsadf.yaproc.command.ACommand;
import com.lsadf.yaproc.command.YaprocCommand;
import com.lsadf.yaproc.file.ContentMap;
import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.util.FileHandlerUtils;
import com.lsadf.yaproc.util.FileUtils;
import com.lsadf.yaproc.util.ValidationUtils;
import picocli.CommandLine;

import java.util.List;
import java.util.logging.Logger;

@CommandLine.Command(
        name = "concat",
        aliases = {"c", "concat"},
        description = "Concatenates multiple files of the same type into a single output file"
)
public class ConcatCommand extends ACommand<List<String>> implements YaprocCommand<List<String>> {

    private static final Logger LOGGER = Logger.getLogger(ConcatCommand.class.getName());

    @CommandLine.Parameters(index = "0", description = "Output file")
    private String output;

    @CommandLine.Parameters(index = "1..*", description = "Input files")
    private List<String> inputList;

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public List<String> getInput() {
        return this.inputList;
    }

    @Override
    public String getOutput() {
        return this.output;
    }

    @Override
    public void init() {
        this.outputFileHandler = FileHandlerUtils.initOutputFileHandlers();
    }

    @Override
    public Integer call() throws Exception {
        try {
            init();
            if (verbose || debug) {
                LOGGER.info("Initializing concat command...");
            }

            // Validate all input files have the same extension
            String commonExtension = validateFileExtensions();
            if (debug) {
                LOGGER.info("Common file extension: " + commonExtension);
            }

            // Validate file format is supported
            ValidationUtils.validateFileFormat(commonExtension);
            if (verbose || debug) {
                LOGGER.info("File format validation successful");
            }

            // Concatenate files
            ContentMap concatMap = new ContentMap();
            for (String file : inputList) {
                FileData fileData = FileUtils.readFile(file);
                ContentMap result = inputFileHandler.handleFile(fileData);
                concatMap.putAll(result);
            }

            // Write concatenated content to output file using the outputFileHandler
            outputFileHandler.handleFile(output, concatMap, isForce());
            if (verbose || debug) {
                LOGGER.info("Successfully wrote concatenated content to: " + getOutput());
            }

            return 0;
        } catch (Exception e) {
            if (debug) {
                LOGGER.severe(e.getMessage());
            } else if (verbose) {
                LOGGER.severe("Error: " + e.getMessage());
            }
            throw e;
        }
    }

    private String validateFileExtensions() {
        if (inputList.size() < 2) {
            throw new IllegalArgumentException("At least one input and one output file must be provided");
        }

        String outputExtension = FileUtils.getFileExtension(inputList.get(0));
        String inputExtension = FileUtils.getFileExtension(inputList.get(1));

        if (!outputExtension.equals(inputExtension)) {
            throw new IllegalArgumentException(
                    String.format("Output file and input file must have the same extension. Found different extensions: %s and %s",
                            outputExtension, inputExtension)
            );
        }

        for (int i = 2; i < inputList.size(); i++) {
            String currentExtension = FileUtils.getFileExtension(inputList.get(i));
            if (!inputExtension.equals(currentExtension)) {
                throw new IllegalArgumentException(
                        String.format("All files must have the same extension. Found different extensions: %s and %s",
                                inputExtension, currentExtension)
                );
            }
        }
        return inputExtension;
    }


}