/**
 * 
 */
package com.nationwide.apigatewayservice.controller;

import static com.nationwide.apigatewayservice.constants.ZuulConstants.API_GATWEWAY_ERRORS;
import static com.nationwide.apigatewayservice.constants.ZuulConstants.DOT;
import static com.nationwide.apigatewayservice.constants.ZuulConstants.GENERIC_ERRORS;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nationwide.apigatewayservice.vo.ErrorVO;
import com.nationwide.apigatewayservice.vo.ServiceResponse;
 

/**
 * <p>
 * The default message is produced by BasicErrorController. In order to have own
 * message format in case of such or other ZuulException we would also like to
 * to achieve this we need to extend AbstractErrorController and the basic
 * implementation of custom exception handling looks like that:
 * </p>
 * 
 * @author Abhishek Mallick
 *
 */
@RestController
public class GatewayErrorController extends AbstractErrorController {

	private final Logger log = LoggerFactory.getLogger(GatewayErrorController.class);

	@Value("${error.path:/error}")
	private String errorPath;

	@Value("${service.errorprefix:nationwide}")
	private String errorPrefix;

	public GatewayErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
	}

	@Override
	public String getErrorPath() {
		return errorPath;
	}

	/**
	 * <p>
	 * This Gateway Error Controller handles the end point that is dispatched from a
	 * OOTB post filter i.e SendErrorFilter.
	 * 
	 * This API listens to the "/error" end point and then handles the exception
	 * with a meaningful message and status.
	 * </p>
	 * 
	 * 
	 * @return <code> ResponseEntity </code>
	 * 
	 */
	@RequestMapping(value = "${error.path:/error}", produces = "application/json;charset=UTF-8")
	public ResponseEntity<ServiceResponse> errorHandler(HttpServletRequest request) {

		log.info("[GatewayErrorController.errorHandler()]");

		HttpStatus status = getStatus(request);
		log.warn("The Exception HTTP status in API Gateway is :: {}", status);

		Throwable exc = (Throwable) request.getAttribute("javax.servlet.error.exception");

		ServiceResponse response = new ServiceResponse();

		final String errorMessage = getErrorMessage(request, exc);

		List<ErrorVO> errorList = new ArrayList<>();
		errorList.add(new ErrorVO(errorPrefix + DOT + status.name().toUpperCase(), errorMessage));

		log.debug("The Exception Message in API Gateway is :: {}", errorMessage);
		if (null != exc) {

			response.setErrors(errorList);

			response.setTimeStamp(String.valueOf(new Timestamp(new Date().getTime())));
			response.setStatus(status.toString());
			response.setErrorType(API_GATWEWAY_ERRORS);
			if (null != exc.getCause()) {
				response.setException(exc.getCause().toString());
				response.setDebugMessage(
						"Excpetion with cause " + exc.getCause() + " and with Message " + exc.getMessage());
			} else {
				response.setException(exc.getMessage());
				response.setDebugMessage("Excpetion with Message " + exc.getMessage());
			}

		}

		else {
			response.setErrors(errorList);

			response.setTimeStamp(String.valueOf(new Timestamp(new Date().getTime())));
			response.setStatus(status.toString());
			response.setErrorType(GENERIC_ERRORS);

		}
		return ResponseEntity.status(status).body(response);
	}

	/**
	 * <p>
	 * Method that returns the Error Message from the Request Object.
	 * </p>
	 * 
	 * @param HttpServletRequest
	 * @return String Error Message.
	 */
	private String getErrorMessage(HttpServletRequest request, Throwable exc) {

		log.info("[Inside GatewayErrorController.getErrorMessage()]");
		String errorMsg = "";

		String exceptionMsg = (String) request.getAttribute("javax.servlet.error.message");
		if (StringUtils.isEmpty(exceptionMsg)) {
			exceptionMsg = "We could not process your request at this moment. Please try after some time.";
		}
		errorMsg = exc != null ? exc.getMessage() : exceptionMsg;
		log.debug("The Exception from API Gateway is :: {}", exc);

		return errorMsg;
	}

}
