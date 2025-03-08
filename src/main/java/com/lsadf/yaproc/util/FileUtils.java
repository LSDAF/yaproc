package com.lsadf.yaproc.util;

import com.lsadf.yaproc.file.FileData;
import lombok.experimental.UtilityClass;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Collectors;

@UtilityClass
public class FileUtils {
  /**
   * Reads the content of a file and returns its type and content.
   *
   * @param file the file to read
   * @return a FileData object with type and content
   */
  public static FileData readFile(File file) throws IOException {
    // Read file content as a string
    var fileInputStream = new FileInputStream(file);
    var inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.ISO_8859_1);

    try (var reader = new BufferedReader(inputStreamReader)) {
      String content = reader.lines().collect(Collectors.joining("\n"));

      // Determine type
      String type = FileUtils.getFileExtension(file);
      // Get filename
      String name = file.getName();

      return new FileData(name, type, content);
    }
  }

  /**
   * Writes content to a file specified by its path.
   *
   * @param file    the file to write to
   * @param content the content to write into the file
   * @param force   whether to overwrite the file if it already exists
   * @return true if the write operation is successful, false otherwise
   */
  public static boolean writeFile(File file, String content, boolean force)
          throws IOException {
    Path filepath = file.toPath();
    if (Files.exists(filepath) && !force) {
      throw new FileAlreadyExistsException(
              "File already exists: " + filepath + ". Use -force to overwrite.");
    }
    Path parent = filepath.getParent();
    if (parent != null) {
      Files.createDirectories(parent);
    }
    Files.writeString(filepath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    return true;
  }

  /**
   * Retrieves the file extension from a given file.
   *
   * @param file the file object
   * @return the file extension (e.g., "json", "yaml", "properties") or an empty string if none is
   * found
   */
  public static String getFileExtension(File file) {
    String filename = file.getName();
    int lastDotIndex = filename.lastIndexOf('.');

    if (lastDotIndex > 0 && lastDotIndex < filename.length() - 1) {
      return filename.substring(lastDotIndex + 1);
    }
    return ""; // No extension found
  }
}
