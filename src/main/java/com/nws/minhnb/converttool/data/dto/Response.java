package com.nws.minhnb.converttool.data.dto;

import lombok.Getter;

/**
 * The Class Response.
 */
@Getter
public class Response<T> {
  /**
   * The Constant CODE_SUCCESS.
   */
  public static final int CODE_SUCCESS = 0;

  public static final int CODE_PERMISSION = 1;

  public static final int CODE_NOT_FOUND = 2;

  public static final int CODE_ALREADY_EXIST = 3;

  public static final int CODE_SEND_TIME_INVALID = 4;

  public static final int CODE_SEND_TEXT_INVALID = 5;

  public static final int CODE_INTERNAL_ERROR = 9999;

  /**
   * The status code.
   */
  private int code;

  /**
   * The data.
   */
  private T data;

  /**
   * Builds a new success response.
   *
   * @return the response.
   */
  public static <T> Response<T> ok() {
    Response<T> res = new Response<T>();
    res.code = CODE_SUCCESS;
    return res;
  }

  /**
   * Builds a new response.
   *
   * @return the response.
   */
  public static <T> Response<T> build() {
    return new Response<T>();
  }

  /**
   * Set code.
   *
   * @param code the code.
   * @return the response.
   */
  public Response<T> code(int code) {
    this.code = code;
    return this;
  }

  /**
   * Set data.
   *
   * @param data the data.
   * @return the response.
   */
  public Response<T> data(T data) {
    this.data = data;
    return this;
  }
}
