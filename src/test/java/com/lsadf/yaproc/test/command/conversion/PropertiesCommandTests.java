package com.lsadf.yaproc.test.command.conversion;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import com.lsadf.yaproc.YaprocApplication;
import com.lsadf.yaproc.util.TestUtils;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

/**
 * Test class for YaprocProperties command functionality. Tests various scenarios including invalid
 * inputs, missing arguments, and successful execution of the properties command.
 */
class PropertiesCommandTests {

  private static final String PROPERTIES = "properties";

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
   * Tests the properties command behavior when no arguments are provided. Expected to exit with a
   * usage error code.
   */
  @Test
  void testPropertiesCommandWithNoArguments() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {PROPERTIES});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    // Verify no output was created
    AssertionsForClassTypes.assertThat(Files.exists(Paths.get("target/test-data/outputs")))
        .isFalse();
  }

  /**
   * Tests the properties command behavior when a required argument is missing. Expected to exit
   * with a usage error code.
   */
  @Test
  void testPropertiesCommandWithMissingArgument() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {PROPERTIES, "test.json"});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    // Verify no output was created
    AssertionsForClassTypes.assertThat(Files.exists(Paths.get("target/test-data/outputs")))
        .isFalse();
  }

  /**
   * Tests the properties command behavior when a required argument is missing even with force flag
   * enabled. Expected to exit with a usage error code.
   */
  @Test
  void testPropertiesCommandWithMissingArgumentAndForce() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {PROPERTIES, "-f", "test.json"});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    // Verify no output was created
    AssertionsForClassTypes.assertThat(Files.exists(Paths.get("target/test-data/outputs")))
        .isFalse();
  }

  /**
   * Tests the properties command behavior when an invalid input file is provided. Expected to exit
   * with a software error code.
   */
  @Test
  void testPropertiesCommandWithInvalidInputFile() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(
                  new String[] {
                    PROPERTIES, "target/test-data/nonexisting.json", "test.properties"
                  });
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.SOFTWARE);
    // Verify no output was created
    AssertionsForClassTypes.assertThat(Files.exists(Paths.get("target/test-data/outputs")))
        .isFalse();
  }

  /**
   * Tests the properties command behavior with valid arguments. Expected to execute successfully
   * with zero exit code.
   */
  @Test
  void testPropertiesCommandWithValidArguments() throws Exception {
    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(
                  new String[] {
                    PROPERTIES,
                    "target/test-data/inputs/test.json",
                    "target/test-data/outputs/test_output.properties",
                    "-fdv"
                  });
            });

    assertThat(status).isZero();
    // Verify the output file exists
    assertThat(Files.exists(Paths.get("target/test-data/outputs/test_output.properties"))).isTrue();
  }

  /**
   * Tests the properties command behavior when processing a malformed properties file. This test
   * verifies that the command properly handles invalid properties syntax and returns an appropriate
   * error code.
   *
   * @throws Exception if there is an unexpected error during test execution
   */
  @Test
  void testPropertiesCommandWithMalformedPropertiesInput() throws Exception {
    String inputPath = "target/test-data/inputs/malformed/malformed.properties";
    String outputPath = "target/test-data/outputs/test_output.properties";

    int status =
        SystemLambda.catchSystemExit(
            () -> {
              YaprocApplication.main(new String[] {PROPERTIES, inputPath, outputPath});
            });

    assertThat(status).isEqualTo(CommandLine.ExitCode.SOFTWARE);
    // Verify that no output file was created due to the error
    assertThat(Files.exists(Paths.get(outputPath))).isFalse();
  }
}
