package com.matheusfelixr.authentication.model.dto;

import lombok.Data;

@Data
public class MessageDTO {

    private String message;

    public MessageDTO(String message) {
        this.message = message;
    }
}
