package com.nws.minhnb.converttool.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OutputDataService {
  /**
   * Todo
   *
   * @param file the input data.
   * @return the file output data.
   */
  List<String> getOutputData(MultipartFile file);
}
