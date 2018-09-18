/**
 * 
 */
package com.nationwide.apigatewayservice.vo;

/**
 * <p>
 * Custom Exception VO for Gateway Application.
 * 
 * A simple POJO to send JSON response to HTTP requests in case of any
 * exceptions.. This POJO enables us to send messages and error codes with the
 * actual objects in the application.
 * </p>
 * 
 * @author Abhishek Mallick
 * 
 */
public class ExceptionResponse {

	/**
	 * A HttpStatus containing error code.
	 */
	private int statusCode;

	/**
	 * A String containing error message.
	 */
	private String message;

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
