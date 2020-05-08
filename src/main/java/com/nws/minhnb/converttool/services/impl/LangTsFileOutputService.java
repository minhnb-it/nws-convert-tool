package com.nws.minhnb.converttool.services.impl;

import com.nws.minhnb.converttool.data.constants.ConvertConstants;
import com.nws.minhnb.converttool.data.dto.ConvertDto;
import com.nws.minhnb.converttool.data.dto.LineDto;
import com.nws.minhnb.converttool.services.OutputDataService;
import com.nws.minhnb.converttool.utils.CheckDataUtil;
import com.nws.minhnb.converttool.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service("langTsFileOutputService")
public class LangTsFileOutputService implements OutputDataService {
  @Override
  public List<String> getOutputData(MultipartFile file) {
    List<LineDto> content = ConvertUtil.toListData(file);

    if (CheckDataUtil.isNullOrEmpty(content)) {
      return null;
    }

    List<LineDto> linesTitle = content.stream()
        .filter(line ->
            CheckDataUtil.isLineContainsAllKeys(line.getContent(), Collections.<String>singletonList(ConvertConstants.TITLE_SEARCH_KEY))
        )
        .collect(Collectors.toList());

    if (CheckDataUtil.isNullOrEmpty(linesTitle)) {
      return null;
    }

    List<String> listJapanText = getStrings(linesTitle, ConvertConstants.TITLE_SEARCH_KEY);

    if (CheckDataUtil.isNullOrEmpty(listJapanText)) {
      return null;
    }

    ConvertDto convertDto = ConvertDto.builder()
        .listId(this.generateListIdTitle(listJapanText.size()))
        .listJapanText(listJapanText)
        .outputTemplate(ConvertConstants.TITLE_OUTPUT_TEMPLATE)
        .idKeyReplacement(ConvertConstants.TITLE_ID_KEY)
        .build();

    List<String> outputData = ConvertUtil.getOutputData(convertDto);
    outputData.addAll(convertLabel(content));
    return outputData;
  }

  //case line: id="id-xxx" ... label="label-xxx"
  private List<String> convertLabel(List<LineDto> content) {
    List<LineDto> inputData = content.stream()
        .filter(line ->
            CheckDataUtil.isLineContainsAllKeys(line.getContent(), Arrays.<String>asList(ConvertConstants.ID_SEARCH_KEY, ConvertConstants.LABEL_SEARCH_KEY))
        )
        .collect(Collectors.toList());

    if (CheckDataUtil.isNullOrEmpty(inputData)) {
      return null;
    }

    List<String> listJapanText = getStrings(inputData, ConvertConstants.LABEL_SEARCH_KEY);
    List<String> listId = getStrings(inputData, ConvertConstants.ID_SEARCH_KEY);

    if (CheckDataUtil.isNullOrEmpty(listJapanText) || CheckDataUtil.isNullOrEmpty(listId)) {
      return null;
    }

    ConvertDto convertDto = ConvertDto.builder()
        .listId(listId)
        .listJapanText(listJapanText)
        .outputTemplate(ConvertConstants.LABEL_OUTPUT_TEMPLATE)
        .idKeyReplacement(ConvertConstants.LABEL_ID_KEY)
        .build();

    return ConvertUtil.getOutputData(convertDto);
  }

  private List<String> getStrings(List<LineDto> inputData, String key) {
    List<String> listOutputData = new ArrayList<>();
    try {
      inputData.forEach(line -> {
        int startIndexJapanText = line.getContent().indexOf(key) + key.length();
        String temp = line.getContent().substring(startIndexJapanText);
        int endIndexJapanText = temp.indexOf("\"");
        String japanTextTitle = temp.substring(0, endIndexJapanText);
        if (!japanTextTitle.isEmpty()) {
          listOutputData.add(japanTextTitle);
        }
      });
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Error get output data");
      e.printStackTrace();
      return null;
    }
    return listOutputData;
  }

  private List<String> generateListIdTitle(int sizeList) {
    if (sizeList == 1) {
      return Collections.singletonList("title");
    }

    List<String> listId = new ArrayList<>();
    for (int i = 0; i < sizeList; i++) {
      if (i < 9) {
        listId.add("title_0" + (i + 1));
      } else {
        listId.add("title_" + (i + 1));
      }
    }
    return listId;
  }
}
