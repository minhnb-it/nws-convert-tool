package com.nws.minhnb.converttool.data.constants;

/**
 * The Enum ConvertState
 */
public enum OutputDataType {
  LANG_TS_FILE,
  COMPONENT_VUE_FILE;

  public static boolean contains(String test) {
    for (OutputDataType value : OutputDataType.values()) {
      if (value.name().equals(test)) {
        return true;
      }
    }
    return false;
  }
}

