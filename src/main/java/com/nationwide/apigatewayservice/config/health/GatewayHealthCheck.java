/**
 * 
 */
package com.nationwide.apigatewayservice.config.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * <ol>
 * <li>Health Check Class for API-Gateway.</li>
 * 
 * <li>The /health endpoint is used to check the health or state of the running
 * application. It’s usually exercised by monitoring software to alert us if the
 * running instance goes down or gets unhealthy for other reasons. E.g.
 * Connectivity issues with our DB, lack of disk space.</li>
 * 
 * <li>This health information is collected from all the beans implementing the
 * HealthIndicator interface configured in our application context.</li>
 * </ol>
 * 
 * @author Abhishek Mallick
 *
 */
@Component
public class GatewayHealthCheck implements HealthIndicator {

	@Override
	public Health health() {
		return Health.up().build();
	}
}
