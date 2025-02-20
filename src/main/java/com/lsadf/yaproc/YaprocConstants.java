package com.lsadf.yaproc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class YaprocConstants {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class ExitStatus {
    public static final String SUCCESS = "SUCCESS";
    public static final String FILE_NOT_FOUND = "FILE_NOT_FOUND";
    public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    public static final String INVALID_COMMAND = "INVALID_COMMAND";
  }
}
