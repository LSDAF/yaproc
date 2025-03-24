package com.lsadf.yaproc.file;

import lombok.Data;

/**
 * Represents the content and metadata of a file.
 *
 * <p>This class encapsulates the basic details about a file, including its name, type, and content.
 * It is designed as an immutable data structure to ensure that file information remains consistent
 * and unaltered after instantiation.
 *
 * <p>Key characteristics: - `name`: The name of the file, usually including the file extension. -
 * `type`: A string representing the type of the file, such as a file extension (e.g., "json",
 * "yml", "txt"). - `content`: The actual content of the file, represented as a string.
 *
 * <p>Common use cases for this class include: - Representing files being processed, such as input
 * for parsing or conversion. - Passing basic file data to components or services that handle file
 * operations. - Facilitating content extraction, validation, or transformation workflows.
 */
@Data
public class FileData {
  private final String name;
  private final String type;
  private final String content;
}
