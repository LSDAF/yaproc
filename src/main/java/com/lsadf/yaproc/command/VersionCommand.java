package com.lsadf.yaproc.command;

import com.lsadf.yaproc.YaprocVersionProvider;
import java.util.Arrays;
import java.util.concurrent.Callable;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
    name = "version",
    aliases = {"v", "version"},
    description = "Prints the application version")
public class VersionCommand implements Callable<Integer> {

  private YaprocVersionProvider versionProvider;

  public void init() {
    versionProvider = new YaprocVersionProvider();
  }

  public Integer call() throws Exception {
    init();
    var version = versionProvider.getVersion();
    Arrays.stream(version).forEach(log::info);
    return 0;
  }
}
