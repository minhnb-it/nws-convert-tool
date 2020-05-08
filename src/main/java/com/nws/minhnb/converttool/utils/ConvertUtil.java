package com.nws.minhnb.converttool.utils;

import com.nws.minhnb.converttool.data.constants.ConvertConstants;
import com.nws.minhnb.converttool.data.dto.ConvertDto;
import com.nws.minhnb.converttool.data.dto.LineDto;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConvertUtil {

  private static boolean isCommentLine(String lineCheck) {
    return lineCheck.contains(ConvertConstants.OPEN_COMMENT_KEY) && lineCheck.contains(ConvertConstants.CLOSE_COMMENT_KEY);
  }

  //get raw data and filter content
  public static List<LineDto> toListData(MultipartFile file) {
    File temp = new File(UUID.randomUUID().toString() + ".txt");
    try {
      FileCopyUtils.copy(file.getBytes(), temp);
      List<String> lines = Files.readAllLines(temp.toPath(), StandardCharsets.UTF_8);
      temp.delete();

      if (CheckDataUtil.isNullOrEmpty(lines)) {
        return null;
      }

      List<LineDto> listData = new ArrayList<>();

      boolean isStartGetData = false;

      for (int i = 0; i < lines.size(); i++) {
        String content = lines.get(i);

        // ignore comment info
        if (!isStartGetData && content.contains(ConvertConstants.OPEN_APPLICATION_TAG_KEY)) {
          isStartGetData = true;
        }

        // ignore comment line
        if (isStartGetData && !isCommentLine(content)) {
          listData.add(LineDto.builder().number(i + 1).content(content).build());
        }
      }

      return listData;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static List<String> getOutputData(ConvertDto convertDto) {
    if (!convertDto.isValid()) {
      return null;
    }

    List<String> listOutputData = new ArrayList<>();

    for (int i = 0; i < convertDto.getSizeList(); i++) {
      listOutputData.add(convertDto.getOutputTemplate()
          .replace(convertDto.getIdKeyReplacement(), convertDto.getListId().get(i))
          .replace(ConvertConstants.JAPAN_TEXT_KEY, convertDto.getListJapanText().get(i)));
    }

    return listOutputData;
  }
}
