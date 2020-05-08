package com.nws.minhnb.converttool.data.dto;

import lombok.Builder;
import lombok.Data;

/**
 * The Class LineDto.
 */
@Data
@Builder
public class LineDto {
  private int number;
  private String content;
}
