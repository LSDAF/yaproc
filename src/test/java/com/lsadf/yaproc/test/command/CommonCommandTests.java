package com.lsadf.yaproc.test.command;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import com.lsadf.yaproc.YaprocApplication;
import com.lsadf.yaproc.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CommonCommandTests {

  /**
   * Cleans the default output directory before each test execution. This helps ensure that the test
   * environment is in a clean state by removing any previously generated files or artifacts in the
   * default output directory.
   *
   * <p>Utilizes the {@link TestUtils#cleanDefaultOutputDirectory()} method to delete all files and
   * directories within the default output directory.
   *
   * <p>The default output directory is typically used for test data and is located at
   * "target/test-data/outputs".
   *
   * @throws RuntimeException if there is an error during the cleaning process
   */
  @BeforeEach
  void cleanOutputDirectory() {
    TestUtils.cleanDefaultOutputDirectory();
  }

  @Test
  void testHelp() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {"help"});
            });

    assertThat(status).isZero();
    // Verify no output was created
    assertThat(Files.exists(Paths.get("target/test-data/outputs"))).isFalse();
  }

  @Test
  void testMissingCommand() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    // Verify no output was created
    assertThat(Files.exists(Paths.get("target/test-data/outputs"))).isFalse();
  }

  @Test
  void testInvalidCommand() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {"invalid"});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    // Verify no output was created
    assertThat(Files.exists(Paths.get("target/test-data/outputs"))).isFalse();
  }
}
