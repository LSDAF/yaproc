package com.lsadf.yaproc.command.conversion;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import com.lsadf.yaproc.YaprocApplication;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JsonCommandTests {

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
              YaprocApplication.main(new String[] {"json", "target/test-data/inputs/nonexisting.properties", "target/test-data/outputs/test.json"});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.SOFTWARE);
  }

  @Test
  void testJsonCommandWithValidArguments() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {"json", "target/test-data/inputs/test.properties", "target/test-data/outputs/test_output.json", "-fdv"});
            });

    assertThat(status).isZero();
  }
}
