package com.matheusfelixr.authentication.model.DTO.config;

import lombok.Data;

import java.util.List;

@Data
public class EmailFormatDTO {

	private String sender;
	
	private List<String> recipients;
	
	private String subject;

	private String body;

}
