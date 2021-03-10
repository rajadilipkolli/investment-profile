package com.zakura.apigateway.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "timestamp", "status", "error", "exception", "message", "path" })
public class ErrorResponse {

	@JsonProperty("timestamp")
	private ZonedDateTime timestamp;

	@JsonProperty("status")
	private Integer status;

	@JsonProperty("error")
	private String error;

	@JsonProperty("exception")
	private String exception;

	@JsonProperty("message")
	private String message;

	@JsonProperty("path")
	private String path;

	public ErrorResponse() {
		super();
		setTimestamp(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ZoneId.SHORT_IDS.get("IST"))));

	}

	@JsonProperty("timestamp")
	private void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@JsonProperty("timestamp")
	private ZonedDateTime getTimestamp() {
		return timestamp;
	}

	@JsonProperty("status")
	public Integer getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(Integer status) {
		this.status = status;
	}

	@JsonProperty("error")
	public String getError() {
		return error;
	}

	@JsonProperty("error")
	public void setError(String error) {
		this.error = error;
	}

	@JsonProperty("exception")
	public String getException() {
		return exception;
	}

	@JsonProperty("exception")
	public void setException(String exception) {
		this.exception = exception;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty("path")
	public String getPath() {
		return path;
	}

	@JsonProperty("path")
	public void setPath(String path) {
		this.path = path;
	}

}
