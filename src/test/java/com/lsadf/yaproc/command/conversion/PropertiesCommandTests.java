package com.lsadf.yaproc.command.conversion;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import com.lsadf.yaproc.YaprocApplication;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for YaprocProperties command functionality.
 * Tests various scenarios including invalid inputs, missing arguments,
 * and successful execution of the properties command.
 */
class PropertiesCommandTests {

    /**
     * Tests the properties command behavior when no arguments are provided.
     * Expected to exit with a usage error code.
     */
    @Test
    void testPropertiesCommandWithNoArguments() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{"properties"});
        });

        assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    }

    /**
     * Tests the properties command behavior when a required argument is missing.
     * Expected to exit with a usage error code.
     */
    @Test
    void testPropertiesCommandWithMissingArgument() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{"properties", "test.json"});
        });

        assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    }

    /**
     * Tests the properties command behavior when a required argument is missing
     * even with force flag enabled.
     * Expected to exit with a usage error code.
     */
    @Test
    void testPropertiesCommandWithMissingArgumentAndForce() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{"properties", "-f", "test.json"});
        });

        assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    }

    /**
     * Tests the properties command behavior when an invalid input file is provided.
     * Expected to exit with a software error code.
     */
    @Test
    void testPropertiesCommandWithInvalidInputFile() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{"properties", "target/test-data/nonexisting.json", "test.properties"});
        });

        assertThat(status).isEqualTo(CommandLine.ExitCode.SOFTWARE);
    }

    /**
     * Tests the properties command behavior with valid arguments.
     * Expected to execute successfully with zero exit code.
     */
    @Test
    void testPropertiesCommandWithValidArguments() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{"properties", "target/test-data/inputs/test.json", "target/test-data/outputs/test_output.properties", "-fdv"});
        });

        assertThat(status).isZero();
    }

}