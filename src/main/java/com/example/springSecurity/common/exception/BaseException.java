package com.example.springSecurity.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends Exception {

  private BaseResponseStatus baseResponseStatus;
}
