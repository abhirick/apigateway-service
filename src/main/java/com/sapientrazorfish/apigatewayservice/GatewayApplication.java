package com.sapientrazorfish.apigatewayservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.client.RestTemplate;

import com.sapientrazorfish.apigatewayservice.filters.pre.SimpleFilter;

@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	@Autowired
	private OAuth2ClientProperties clientProperties;

	@Autowired
	private ResourceServerProperties resourceProperties;

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public SimpleFilter simpleFilter() {
		return new SimpleFilter();
	}

	@Bean
	UserInfoRestTemplateCustomizer userInfoRestTemplateCustomizer(LoadBalancerInterceptor loadBalancerInterceptor) {
		return template -> {
			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
			interceptors.add(loadBalancerInterceptor);
			AccessTokenProviderChain accessTokenProviderChain = Stream
					.of(new AuthorizationCodeAccessTokenProvider(), new ImplicitAccessTokenProvider(),
							new ResourceOwnerPasswordAccessTokenProvider(), new ClientCredentialsAccessTokenProvider())
					.peek(tp -> tp.setInterceptors(interceptors))
					.collect(Collectors.collectingAndThen(Collectors.toList(), AccessTokenProviderChain::new));
			template.setAccessTokenProvider(accessTokenProviderChain);
		};
	}

	@Bean
	@Primary
	ResourceServerTokenServices resourceServerTokenServices(
			LoadBalancerInterceptor loadBalancerInterceptor) {
		RestTemplate tokenRest = new RestTemplate();
		tokenRest.setInterceptors(Arrays.asList(loadBalancerInterceptor));
		RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
		remoteTokenServices.setRestTemplate(tokenRest);
		remoteTokenServices.setClientId(clientProperties.getClientId());
		remoteTokenServices.setClientSecret(clientProperties.getClientSecret());
		remoteTokenServices.setCheckTokenEndpointUrl(resourceProperties.getTokenInfoUri());
		return remoteTokenServices;
	}

}
