
package com.matheusfelixr.authentication.model.dto.config;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ResponseApi<T> {

	private T data;

	private List<String> errors;

	public ResponseApi() {

	}

	public List<String> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<String>();
		}

		return this.errors;
	}

}
