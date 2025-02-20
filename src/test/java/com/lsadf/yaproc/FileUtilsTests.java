package com.lsadf.yaproc;

import com.lsadf.yaproc.file.FileData;
import com.lsadf.yaproc.util.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileUtilsTests {
    // Add tests for readFile, writeFile, and getFileExtension methods
    // Test for readFile with invalid file path

    @Test
    void testReadFileWithValidFilePath() {
        String filePath = "test-data/inexisting";
        assertThrows(FileNotFoundException.class, () -> FileUtils.readFile(filePath));
    }

    @Test
    void testReadFileWithValidFile() throws IOException {
        String filePath = "test-data/test.json";
        FileData fileData = FileUtils.readFile(filePath);
        assertThat(fileData.getContent()).isEqualTo("{\n  \"test\": \"test\"\n}\n");
        assertThat(fileData.getType()).isEqualTo("json");
    }

    // Test for writeFile with invalid file path

    // Test for getFileExtension with invalid file path

}
