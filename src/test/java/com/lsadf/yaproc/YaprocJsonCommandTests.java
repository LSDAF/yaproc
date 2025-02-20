package com.lsadf.yaproc;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class YaprocJsonCommandTests {

  @Test
  void testJsonCommandWithNoArguments() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {"json"});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
  }

  @Test
  void testJsonCommandWithMissingArgument() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {"json", "test.properties"});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
  }

  @Test
  void testJsonCommandWithMissingArgumentAndForce() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {"json", "-f", "test.properties"});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
  }

  @Test
  void testJsonCommandWithInvalidInputFile() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {"json", "nonexisting.properties", "test.json"});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
  }

  @Test
  void testJsonCommandWithValidArguments() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {"json", "test.properties", "test.json"});
            });

    assertThat(status).isZero();
  }
}
