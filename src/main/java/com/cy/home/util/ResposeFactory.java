package com.cy.home.util;

import com.cy.home.domain.DTO.ResponseDTO;

public class ResposeFactory {

    public static ResponseDTO failed(String message) {
        return failed(message,null);
    }

    public static ResponseDTO failed(String message,Object data) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus(0);
        responseDTO.setMsg(message);
        responseDTO.setData(data);
        return responseDTO;
    }
}
