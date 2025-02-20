package com.lsadf.yaproc.util;

import com.lsadf.yaproc.exception.UnsupportedFileFormatException;
import com.lsadf.yaproc.file.FileFormat;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationUtils {
    public static void validateFileFormat(String fileFormat) {
        if (!FileFormat.getValidExtensions().contains(fileFormat)) {
            throw new UnsupportedFileFormatException("Unsupported file format: " + fileFormat);
        }
    }
}
