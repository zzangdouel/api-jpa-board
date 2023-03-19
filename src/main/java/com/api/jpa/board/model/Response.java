package com.api.jpa.board.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    int code; //0:성공, 1:실패
    Boolean status;
    String accountId;
    String message;
}
