package com.hector.test.apache.kafka.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Document(collection = "error")
@JsonPropertyOrder({"message","exception","topic"})
public class Error implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String message,exception,topic;
		
	public Error() {
		super();
	}

	public Error(String message, String exception, String topic) {
		super();
		this.message = message;
		this.exception = exception;
		this.topic = topic;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "Error [message=" + message + ", exception=" + exception + ", topic=" + topic + "]";
	}
	
	public Error errorToLower() {
		return new Error(this.message.toLowerCase(),this.exception.toLowerCase(),this.topic.toLowerCase());
	}
			

}
