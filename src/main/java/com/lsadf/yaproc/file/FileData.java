package com.lsadf.yaproc.file;

import lombok.Data;

@Data
/**
 * A class to hold the MIME type and content of a file.
 */
public class FileData {
    private final String name;
    private final String type;
    private final String content;
}
