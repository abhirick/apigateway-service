package com.nationwide.apigatewayservice.filters.pre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.nationwide.apigatewayservice.constants.ZuulConstants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/** <ol>
 * <li>This custom Route Zuul Filter that gets invoked before routing the request
 * to the underlying services.</li>
 * 
 * <li> Custom Pre filter which adds headers for requests 
 * which will be required while redirecting to underlying services </li>
 * <ol>
 * @author abhi
 *
 */
public class HeaderPreFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(HeaderPreFilter.class);

	@Value("${headers.clientId:fe-api-gateway}")
	private String clientId;

	@Value("${headers.accept:application/json}")
	private String accept;


	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 * This method adds ClientId and Accept as headers before routing 
	 * the request for underlying services
	 */
	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();

		if (log.isInfoEnabled())
			log.info("[HeaderPreFilter.run() Adding headers to the request]");
		
		// Add Accept and/or Client Id headers		
		context.addZuulRequestHeader(ZuulConstants.ACCEPT, accept);
		context.addZuulRequestHeader(ZuulConstants.CLIENT_ID, clientId);

		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return ZuulConstants.FILTER_ORDER_THREE;
	}

}
