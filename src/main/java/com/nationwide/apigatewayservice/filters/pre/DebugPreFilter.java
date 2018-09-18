package com.nationwide.apigatewayservice.filters.pre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.pre.DebugFilter;

/**
 * <p>
 * Filter that extends the OOTB DebugFilter's shouldFilter method to add custom
 * logic.
 * </p>
 * 
 * @author abhi
 *
 */
public class DebugPreFilter extends DebugFilter {

	private static Logger log = LoggerFactory.getLogger(DebugPreFilter.class);

	/**
	 * Overriding the default behavior of Debug Filter which requires debug request
	 * parameter
	 */
	@Override
	public boolean shouldFilter() {

		log.info("[DebugPreFilter.shouldFilter() Logging enabled: {} ]");
		return Boolean.TRUE;
	}

}
