package com.gateway_server;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public RouteLocator easybankRouteConfig(RouteLocatorBuilder builder) {
		
		return builder.routes()
				.route(p->p.
						path("/eazybank/accounts/**").
						filters(f->f.rewritePath("/eazybank/accounts/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								)
						.uri("lb://ACCOUNTS")
						)
				
				.route(p->p.
						path("/eazybank/loans/**").
						filters(f->f.rewritePath("/eazybank/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								)
						.uri("lb://LOANS")
						)
				
				.route(p->p.
						path("/eazybank/cards/**")
						.filters(f->
						f.rewritePath("/eazybank/cards/(?<segment>.*)","/${segment}")
						.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								)
						.uri("lb://CARDS")
						).build();
	}

}
