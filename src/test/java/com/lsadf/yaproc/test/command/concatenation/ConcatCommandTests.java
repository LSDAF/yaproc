package com.lsadf.yaproc.test.command.concatenation;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import com.lsadf.yaproc.YaprocApplication;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ConcatCommandTests {

    /**
     * Tests the concat command behavior when no arguments are provided.
     * Expected to exit with a usage error code.
     */
    @Test
    void testConcatCommandWithNoArguments() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{"concat"});
        });

        assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    }

    /**
     * Tests the concat command behavior when only one file is provided.
     * Expected to exit with a usage error code as concat requires at least 2 files.
     */
    @Test
    void testConcatCommandWithSingleFile() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{"concat", "test.properties"});
        });

        assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    }

    /**
     * Tests the concat command behavior with force flag but insufficient files.
     * Expected to exit with a usage error code.
     */
    @Test
    void testConcatCommandWithInsufficientFilesAndForce() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{"concat", "-f", "test.properties"});
        });

        assertThat(status).isEqualTo(CommandLine.ExitCode.USAGE);
    }

    /**
     * Tests the concat command behavior when input files have different extensions.
     * Expected to exit with a software error code.
     */
    @Test
    void testConcatCommandWithDifferentFileTypes() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{
                    "concat",
                    "target/test-data/outputs/output.properties",
                    "target/test-data/inputs/test1.properties",
                    "target/test-data/inputs/test2.json",
            });
        });

        assertThat(status).isEqualTo(CommandLine.ExitCode.SOFTWARE);
    }

    /**
     * Tests the concat command behavior when one of the input files doesn't exist.
     * Expected to exit with a software error code.
     */
    @Test
    void testConcatCommandWithNonexistentFile() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{
                    "concat",
                    "target/test-data/outputs/output.properties",
                    "target/test-data/inputs/test1.properties",
                    "target/test-data/inputs/nonexisting.properties"
            });
        });

        assertThat(status).isEqualTo(CommandLine.ExitCode.SOFTWARE);
    }

    /**
     * Tests the concat command behavior with valid arguments and same file types.
     * Expected to execute successfully with zero exit code.
     */
    @Test
    void testConcatCommandWithValidArguments() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{
                    "concat",
                    "target/test-data/outputs/concatenated.properties",
                    "target/test-data/inputs/test1.properties",
                    "target/test-data/inputs/test2.properties",
                    "-fdv"
            });
        });

        assertThat(status).isZero();
    }

    /**
     * Tests the concat command with multiple input files of the same type.
     * Expected to execute successfully with zero exit code.
     */
    @Test
    void testConcatCommandWithMultipleInputFiles() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            YaprocApplication.main(new String[]{
                    "concat",
                    "target/test-data/outputs/concatenated_multiple.properties",
                    "target/test-data/inputs/test1.properties",
                    "target/test-data/inputs/test2.properties",
                    "target/test-data/inputs/test3.properties",
                    "-fdv"
            });
        });

        assertThat(status).isZero();
    }

}
