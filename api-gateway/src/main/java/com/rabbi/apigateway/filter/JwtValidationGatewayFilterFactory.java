package com.rabbi.apigateway.filter;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JwtValidationGatewayFilterFactory extends
        AbstractGatewayFilterFactory<Object> {

    private final WebClient webClient;

    /*
      Create a WebClient connected to the Auth Service.
      The base URL is read from the application configuration: auth.service.url.
      This WebClient will be used later to call /validate on the Auth Service.
    */
    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder,
                                             @Value("${auth.service.url}") String authServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            // Extract the Authorization header from the incoming request
            String token =
                    exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

      /*
        If the token is missing or doesn’t begin with "Bearer ",
        immediately stop processing and return 401 Unauthorized.
      */
            if(token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

      /*
        Send the token to the Auth Service /validate endpoint.
        Pass the Authorization header as-is.
        If the response is successful → continue the filter chain (request passes through).
        The call is non-blocking because WebClient uses reactive programming.
      */
            return webClient.get()
                    .uri("/validate")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve()
                    .toBodilessEntity()        // No body expected, only status code
                    .then(chain.filter(exchange)); // Continue request if validation succeeds
        };
    }
}
