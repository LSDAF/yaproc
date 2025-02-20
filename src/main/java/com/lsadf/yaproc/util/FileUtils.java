package com.lsadf.yaproc.util;

import com.lsadf.yaproc.file.FileData;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.*;

@UtilityClass
public class FileUtils {
  /**
   * Reads the content of a file and returns its type and content.
   *
   * @param filename the name of the file to read
   * @return a FileData object with type and content
   */
  public static FileData readFile(String filename) throws IOException {
    Path path = Paths.get(filename);
    // Read file content as a string
    String content = Files.readString(path);
    // Determine type
    String type = FileUtils.getFileExtension(filename);
    // Get filename
    String name = path.getFileName().toString();

    return new FileData(name, type, content);
  }

  /**
   * Writes content to a file specified by its path.
   *
   * @param path the path of the file to write to
   * @param content the content to write into the file
   * @param force whether to overwrite the file if it already exists
   * @return true if the write operation is successful, false otherwise
   */
  public static boolean writeFile(String path, String content, boolean force)
      throws IOException {
    Path filepath = Paths.get(path);
    if (Files.exists(filepath) && !force) {
      throw new FileAlreadyExistsException(
          "File already exists: " + filepath + ". Use -force to overwrite.");
    }
    Files.writeString(filepath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    return true;
  }

  /**w
   * Retrieves the file extension from a given filename or path.
   *
   * @param filepath the path or name of the file
   * @return the file extension (e.g., "json", "yaml", "properties") or an empty string if none is
   *     found
   */
  public static String getFileExtension(String filepath) {
    Path path = Paths.get(filepath);
    String filename = path.getFileName().toString();
    int lastDotIndex = filename.lastIndexOf('.');

    if (lastDotIndex > 0 && lastDotIndex < filename.length() - 1) {
      return filename.substring(lastDotIndex + 1);
    }
    return ""; // No extension found
  }
}
