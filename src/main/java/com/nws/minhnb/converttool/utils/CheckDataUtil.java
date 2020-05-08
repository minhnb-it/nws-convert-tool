package com.nws.minhnb.converttool.utils;

import java.util.List;
import java.util.Map;

public class CheckDataUtil {

  /**
   * Check data is null or empty.
   *
   * @param <T>  the type of the data
   * @param data the data need check
   * @return {@code true} if data is null or empty, otherwise {@code false}
   */
  public static <T> boolean isNullOrEmpty(T data) {
    if (data == null) {
      return true;
    }

    if (data instanceof String) {
      return ((String) data).isEmpty();
    }

    if (data instanceof List) {
      return ((List) data).isEmpty();
    }

    if (data instanceof Map) {
      return ((Map) data).isEmpty();
    }

    return false;
  }

  /**
   * Returns true if and only if this line contains all item in list key check.
   *
   * @param line      the content of line.
   * @param keysCheck the list key check.
   * @return true if this line contains all item in {@code keysCheck}, false otherwise.
   */
  public static boolean isLineContainsAllKeys(String line, List<String> keysCheck) {
    for (String key : keysCheck) {
      if (!line.contains(key)) {
        return false;
      }
    }
    return true;
  }
}
