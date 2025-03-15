package com.lsadf.yaproc.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import lombok.experimental.UtilityClass;

/**
 * Utility class providing helper methods for test classes.
 */
@UtilityClass
public class TestUtils {

    /**
     * Cleans the output directory by deleting all files and directories within it.
     * This is useful for tests that need a clean output directory before execution.
     *
     * @param outputDirPath The path to the output directory to clean
     * @throws RuntimeException If there's an error deleting any files
     */
    public static void cleanOutputDirectory(String outputDirPath) {
        Path outputDir = Paths.get(outputDirPath);
        if (Files.exists(outputDir)) {
            try {
                Files.walk(outputDir)
                        .sorted(Comparator.reverseOrder()) // Delete children first
                        .forEach(
                                path -> {
                                    try {
                                        Files.delete(path);
                                    } catch (Exception e) {
                                        throw new RuntimeException("Failed to delete file: " + path, e);
                                    }
                                });
            } catch (Exception e) {
                throw new RuntimeException("Failed to clean output directory: " + outputDirPath, e);
            }
        }
    }

    /**
     * Cleans the default test output directory (target/test-data/outputs).
     *
     * @throws RuntimeException If there's an error deleting any files
     */
    public static void cleanDefaultOutputDirectory() {
        cleanOutputDirectory("target/test-data/outputs");
    }
    
    

}
