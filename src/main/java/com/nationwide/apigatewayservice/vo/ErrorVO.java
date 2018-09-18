package com.nationwide.apigatewayservice.vo;

import java.io.Serializable;
import java.util.List;

/**
 * The Class ErrorVO
 */

public class ErrorVO implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1355178152687214844L;

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	public ErrorVO() {}

	public ErrorVO(String key, String message) {
		super();
		this.key = key;
		this.message = message;
	}

	
	public ErrorVO(String key, String message, List<String> data ) {
		super();
		this.key = key;
		this.message = message;
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "ErrorVO [key=" + key + ", message=" + message + "]";
	}

	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public List <String> getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param message the new data
	 */
	public void setData(List <String> data) {
		this.data = data;
	}



	/** The key. */
	private String key;

	/** The message. */
	private String message;
	
	/** The list of data*/
	private List <String> data;

}
