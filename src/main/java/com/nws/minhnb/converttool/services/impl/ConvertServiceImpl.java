package com.nws.minhnb.converttool.services.impl;

import com.nws.minhnb.converttool.data.constants.OutputDataType;
import com.nws.minhnb.converttool.data.dto.InputDto;
import com.nws.minhnb.converttool.services.ConvertService;
import com.nws.minhnb.converttool.services.OutputDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConvertServiceImpl implements ConvertService {

  @Autowired
  @Qualifier("langTsFileOutputService")
  private OutputDataService langTsFileOutputService;

  @Autowired
  @Qualifier("componentVueFileOutputService")
  private OutputDataService componentVueFileOutputService;

  @Override
  public List<String> convert(InputDto inputDto, MultipartFile file) {
    OutputDataService outputDataService = this.toOutputDataService(inputDto.getOutputDataType());
    if (outputDataService == null) {
      return new ArrayList<>();
    }
    return outputDataService.getOutputData(file);
  }

  /**
   * Get OutputDataService by OutputDataType.
   *
   * @param outputDataType the OutputDataType.
   * @return the OutputDataService or {@code null}.
   */
  private OutputDataService toOutputDataService(OutputDataType outputDataType) {
    switch (outputDataType) {
      case LANG_TS_FILE:
        return this.langTsFileOutputService;
      case COMPONENT_VUE_FILE:
        return this.componentVueFileOutputService;
      default:
        return null;
    }
  }
}
