package com.appointment.gatewayServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

	@Bean
	public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/hms/booking/**")
						.filters(f -> f.rewritePath("/hms/booking/(?<segment>.*)", "/${segment}")
								.circuitBreaker(config -> config.setName("bookingCircuitBreaker")
										.setFallbackUri("forward:/contactSupport").setStatusCodes(Set.of("500", "502", "503", "504"))))
						.uri("http://localhost:8080")).build();
	}

}
