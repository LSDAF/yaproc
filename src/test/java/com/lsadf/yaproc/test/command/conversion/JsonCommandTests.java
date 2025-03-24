package com.lsadf.yaproc.test.command.conversion;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import com.lsadf.yaproc.YaprocApplication;
import com.lsadf.yaproc.util.TestUtils;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

class JsonCommandTests {

  private static final String JSON = "json";

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
  void testJsonCommandWithNoArguments() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {JSON});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    // Verify no output was created
    assertThat(Files.exists(Paths.get("target/test-data/outputs"))).isFalse();
  }

  @Test
  void testJsonCommandWithMissingArgument() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {JSON, "test.properties"});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    // Verify no output was created
    assertThat(Files.exists(Paths.get("target/test-data/outputs"))).isFalse();
  }

  @Test
  void testJsonCommandWithMissingArgumentAndForce() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {JSON, "-f", "test.properties"});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    // Verify no output was created
    assertThat(Files.exists(Paths.get("target/test-data/outputs"))).isFalse();
  }

  @Test
  void testJsonCommandWithInvalidInputFile() throws Exception {
    String inputPath = "target/test-data/nonexisting.properties";
    String outputPath = "target/test-data/outputs/test.json";
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {JSON, inputPath, outputPath});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.SOFTWARE);
    // Verify that no output file was created
    assertThat(Files.exists(Paths.get(outputPath))).isFalse();
  }

  @Test
  void testJsonCommandWithValidArguments() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(
                  new String[] {
                    JSON,
                    "target/test-data/inputs/test.properties",
                    "target/test-data/outputs/test_output.json",
                    "-fdv"
                  });
            });

    assertThat(status).isZero();
    // Verify the output file exists
    assertThat(Files.exists(Paths.get("target/test-data/outputs/test_output.json"))).isTrue();
  }

  /**
   * Tests the JSON command's behavior when processing a malformed JSON file. This test verifies
   * that the command properly handles invalid JSON syntax and returns an appropriate error code.
   *
   * @throws Exception if there is an unexpected error during test execution
   */
  @Test
  void testJsonCommandWithMalformedJsonInput() throws Exception {
    String inputPath = "target/test-data/inputs/malformed/malformed.json";
    String outputPath = "target/test-data/outputs/test_output.json";

    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {JSON, inputPath, outputPath});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.SOFTWARE);
    // Verify that no output file was created due to the error
    assertThat(Files.exists(Paths.get(outputPath))).isFalse();
  }
}
