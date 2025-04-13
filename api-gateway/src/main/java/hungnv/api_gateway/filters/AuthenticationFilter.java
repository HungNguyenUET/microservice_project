package hungnv.api_gateway.filters;

import hungnv.api_gateway.exception.ValidationException;
import hungnv.api_gateway.services.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {
    private static final String ID = "id";
    private static final String ROLE = "role";
    private static final String AUTH_HEADER_KEY = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final int TOKEN_INDEX = 7;

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (this.isAuthMissing(request)) {
            throw new ValidationException(HttpStatus.UNAUTHORIZED, "Authorization header is missing in request");
        }

        String authHeader = this.getAuthHeader(request);
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith(TOKEN_PREFIX)) {
            throw new ValidationException(HttpStatus.UNAUTHORIZED, "Authorization header method is incorrect");
        }

        String token = authHeader.substring(TOKEN_INDEX);
        String userName = jwtService.extractUsername(token);

        if (!jwtService.validateToken(token, userName)) {
            throw new ValidationException(HttpStatus.UNAUTHORIZED, "Token invalid");
        }

        populateRequestWithHeader(exchange, token);
        return chain.filter(exchange);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(AUTH_HEADER_KEY);
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(AUTH_HEADER_KEY).get(0);
    }

    private void populateRequestWithHeader(ServerWebExchange exchange, String token) {
        Claims claims = jwtService.extractAllClaims(token);
        exchange.getRequest().mutate()
                .header(ID, String.valueOf(claims.get(ID)))
                .header(ROLE, String.valueOf(claims.get(ROLE)))
                .build();
    }

}
