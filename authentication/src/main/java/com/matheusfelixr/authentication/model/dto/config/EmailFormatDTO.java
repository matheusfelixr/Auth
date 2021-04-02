package com.matheusfelixr.authentication.model.dto.config;

import lombok.Data;

import java.util.List;

@Data
public class EmailFormatDTO {

	private String sender;
	
	private List<String> recipients;
	
	private String subject;

	private String body;

	public EmailFormatDTO() {
	}

	public EmailFormatDTO(String sender, List<String> recipients, String subject, String body) {
		this.sender = sender;
		this.recipients = recipients;
		this.subject = subject;
		this.body = body;
	}

}
