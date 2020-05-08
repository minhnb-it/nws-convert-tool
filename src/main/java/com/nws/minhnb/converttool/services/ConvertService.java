package com.nws.minhnb.converttool.services;

import com.nws.minhnb.converttool.data.dto.InputDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ConvertService {
  /**
   * Convert.
   *
   * @param inputDto the InputDto.
   * @param file     the file.
   * @return the Response.
   */
  List<String> convert(InputDto inputDto, MultipartFile file);
}
