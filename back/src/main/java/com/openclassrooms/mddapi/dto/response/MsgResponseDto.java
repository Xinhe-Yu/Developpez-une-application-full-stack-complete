package com.openclassrooms.mddapi.dto.response;

public class MsgResponseDto implements ApiResponseDto {
  private String message;

  public MsgResponseDto(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
