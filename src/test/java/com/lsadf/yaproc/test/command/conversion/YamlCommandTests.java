package com.lsadf.yaproc.test.command.conversion;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import com.lsadf.yaproc.YaprocApplication;
import com.lsadf.yaproc.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class YamlCommandTests {

  private static final String YAML = "yaml";

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

  /**
   * Tests the yaml command behavior when no arguments are provided. Expected to exit with a usage
   * error code.
   */
  @Test
  void testYamlCommandWithNoArguments() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {YAML});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    // Verify no output was created
    assertThat(Files.exists(Paths.get("target/test-data/outputs"))).isFalse();
  }

  /**
   * Tests the yaml command behavior when a required argument is missing. Expected to exit with a
   * usage error code.
   */
  @Test
  void testYamlCommandWithMissingArgument() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {YAML, "test.properties"});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    // Verify no output was created
    assertThat(Files.exists(Paths.get("target/test-data/outputs"))).isFalse();
  }

  /**
   * Tests the yaml command behavior when a required argument is missing even with force flag
   * enabled. Expected to exit with a usage error code.
   */
  @Test
  void testYamlCommandWithMissingArgumentAndForce() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {YAML, "-f", "test.properties"});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    // Verify no output was created
    assertThat(Files.exists(Paths.get("target/test-data/outputs"))).isFalse();
  }

  /**
   * Tests the yaml command behavior when an invalid input file is provided. Expected to exit with a
   * software error code.
   */
  @Test
  void testYamlCommandWithInvalidInputFile() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(
                  new String[] {
                    YAML,
                    "target/test-data/inputs/nonexisting.properties",
                    "target/test-data/outputs/test.yml"
                  });
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.SOFTWARE);
    // Verify no output was created
    assertThat(Files.exists(Paths.get("target/test-data/outputs"))).isFalse();
  }

  /**
   * Tests the yaml command behavior with valid arguments. Expected to execute successfully with
   * zero exit code.
   */
  @Test
  void testYamlCommandWithValidArguments() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(
                  new String[] {
                    YAML,
                    "target/test-data/inputs/test.properties",
                    "target/test-data/outputs/test_output.yml",
                    "-fdv"
                  });
            });

    assertThat(status).isZero();
    // Verify no output was created
    assertThat(Files.exists(Paths.get("target/test-data/outputs/test_output.yml"))).isTrue();
  }

  /**
   * Tests the YAML command's behavior when processing a malformed YAML file. This test verifies
   * that the command properly handles invalid YAML syntax and returns an appropriate error code.
   *
   * @throws Exception if there is an unexpected error during test execution
   */
  @Test
  void testYamlCommandWithMalformedYamlInput() throws Exception {
    String inputPath = "target/test-data/inputs/malformed/malformed.yaml";
    String outputPath = "target/test-data/outputs/test_output.yaml";

    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {YAML, inputPath, outputPath});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.SOFTWARE);
    // Verify that no output file was created due to the error
    assertThat(Files.exists(Paths.get(outputPath))).isFalse();
  }
}
