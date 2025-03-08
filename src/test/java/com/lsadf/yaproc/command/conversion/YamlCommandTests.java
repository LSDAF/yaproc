package com.lsadf.yaproc.command.conversion;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import com.lsadf.yaproc.YaprocApplication;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class YamlCommandTests {

    /**
     * Tests the yaml command behavior when no arguments are provided.
     * Expected to exit with a usage error code.
     */
    @Test
    void testYamlCommandWithNoArguments() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{"yaml"});
        });

        assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    }

    /**
     * Tests the yaml command behavior when a required argument is missing.
     * Expected to exit with a usage error code.
     */
    @Test
    void testYamlCommandWithMissingArgument() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{"yaml", "test.properties"});
        });

        assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    }

    /**
     * Tests the yaml command behavior when a required argument is missing
     * even with force flag enabled.
     * Expected to exit with a usage error code.
     */
    @Test
    void testYamlCommandWithMissingArgumentAndForce() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{"yaml", "-f", "test.properties"});
        });

        assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    }

    /**
     * Tests the yaml command behavior when an invalid input file is provided.
     * Expected to exit with a software error code.
     */
    @Test
    void testYamlCommandWithInvalidInputFile() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{"yaml", "target/test-data/inputs/nonexisting.properties", "target/test-data/outputs/test.yml"});
        });

        assertThat(status).isEqualTo(CommandLine.ExitCode.SOFTWARE);
    }

    /**
     * Tests the yaml command behavior with valid arguments.
     * Expected to execute successfully with zero exit code.
     */
    @Test
    void testYamlCommandWithValidArguments() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{"yaml", "target/test-data/inputs/test.properties", "target/test-data/outputs/test_output.yml", "-fdv"});
        });

        assertThat(status).isZero();
    }

}
