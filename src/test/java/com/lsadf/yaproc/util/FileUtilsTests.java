package com.lsadf.yaproc.util;

import com.lsadf.yaproc.file.FileData;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileUtilsTests {
    // Add tests for readFile, writeFile, and getFileExtension methods
    // Test for readFile with invalid file path

    @Test
    void testReadFileWithValidFilePath() {
        String filePath = "target/test-data/inexisting";
        File path = new File(filePath);
        assertThrows(FileNotFoundException.class, () -> FileUtils.readFile(path));
    }

    @Test
    void testReadFileWithValidFile() throws IOException {
        String filePath = "target/test-data/inputs/test.json";
        File path = new File(filePath);
        FileData fileData = FileUtils.readFile(path);
        assertThat(fileData.getContent()).isEqualTo("{\n  \"test\": \"test\"\n}");
        assertThat(fileData.getType()).isEqualTo("json");
    }

}
