package com.nws.minhnb.converttool.services.impl;

import com.nws.minhnb.converttool.services.OutputDataService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service("componentVueFileOutputService")
public class ComponentVueFileOutputService implements OutputDataService {
  @Override
  public List<String> getOutputData(MultipartFile file) {
    return null;
  }
}
