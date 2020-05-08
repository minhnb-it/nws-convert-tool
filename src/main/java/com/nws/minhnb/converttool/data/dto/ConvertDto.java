package com.nws.minhnb.converttool.data.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * The Class ConvertDto.
 */
@Data
@Builder
public class ConvertDto {
  private List<String> listId;
  private List<String> listJapanText;
  private String outputTemplate;
  private String idKeyReplacement;

  public boolean isValid() {
    return this.listId.size() == this.listJapanText.size();
  }

  public int getSizeList() {
    return this.listJapanText.size();
  }
}
