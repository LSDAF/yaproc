package com.lsadf.yaproc.command;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import com.lsadf.yaproc.YaprocApplication;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CommonCommandTests {
  @Test
  void testHelp() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {"help"});
            });

    assertThat(status).isZero();
  }

  @Test
  void testMissingCommand() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
  }

  @Test
  void testInvalidCommand() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {"invalid"});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
  }
}
