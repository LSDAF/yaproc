package com.lsadf.yaproc;

import com.lsadf.yaproc.exception.UnsupportedFileFormatException;
import picocli.CommandLine;

public class YaprocExceptionHandler implements CommandLine.IExecutionExceptionHandler {
  @Override
  public int handleExecutionException(
      Exception e, CommandLine commandLine, CommandLine.ParseResult parseResult) throws Exception {
    if (e instanceof java.io.FileNotFoundException) {
      return CommandLine.ExitCode.USAGE;
    } else if (e instanceof UnsupportedFileFormatException) {
      return 3;
    }
    return CommandLine.ExitCode.SOFTWARE;
  }
}
