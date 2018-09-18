package com.nationwide.apigatewayservice.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nationwide.apigatewayservice.filters.pre.DebugPreFilter;
import com.nationwide.apigatewayservice.filters.pre.HeaderPreFilter;

/**
 * <p>
 * Configuration Class that holds all the Custom Bean Initialization.
 * </p>
 * 
 * @author Abhishek Mallick
 *
 */
@Configuration
public class GatewayConfiguration {

	@Configuration
	public static class DebugPreFilterConfiguration {

		@Bean
		@ConditionalOnMissingBean(DebugPreFilter.class)
		public DebugPreFilter debugPreFilter() {
			return new DebugPreFilter();
		}
	}

	@Configuration
	public static class HeaderPreFilterConfiguration {

		@Bean
		@ConditionalOnMissingBean(HeaderPreFilter.class)
		public HeaderPreFilter customHeaderPreFilter() {
			return new HeaderPreFilter();
		}
	}

}
