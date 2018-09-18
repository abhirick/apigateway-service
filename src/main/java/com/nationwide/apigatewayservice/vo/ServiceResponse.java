package com.nationwide.apigatewayservice.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The Class ServiceResponse.
 *
 */
@JsonInclude(Include.NON_NULL)
public class ServiceResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8486754379605235374L;

	/**
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public List<ErrorVO> getErrors() {
		return errors;
	}

	/**
	 * Sets the errors.
	 *
	 * @param errors the new errors
	 */
	public void setErrors(List<ErrorVO> errors) {
		this.errors = errors;
	}

	/** The errors. */
	private List<ErrorVO> errors;
	
	/** The time stamp. */
	private String timeStamp;

	/** The error type. */
	private String errorType;

	/** The http status. */
	private String status;

	/** The exception. */
	private String exception;
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the debug message.
	 *
	 * @return the debugMessage
	 */
	public String getDebugMessage() {
		return debugMessage;
	}

	/**
	 * Sets the debug message.
	 *
	 * @param debugMessage
	 *            the debugMessage to set
	 */
	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	/** The debug message. */
	private String debugMessage;

	/**
	 * Gets the error type.
	 *
	 * @return the errorType
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * Sets the error type.
	 *
	 * @param errorType
	 *            the errorType to set
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	/**
	 * Gets the time stamp.
	 *
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Sets the time stamp.
	 *
	 * @param timeStamp
	 *            the timeStamp to set
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	
	/**
	 * Gets the exception.
	 *
	 * @return the exception
	 */
	public String getException() {
		return exception;
	}

	/**
	 * Sets the exception.
	 *
	 * @param exception the new exception
	 */
	public void setException(String exception) {
		this.exception = exception;
	}

}
