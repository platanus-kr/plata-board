package org.platanus.webboard.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.security.dto.UserClaimDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static java.util.Arrays.stream;

/**
 * 페이지 진입시 토큰 유효성 검증
 */
@Slf4j
public class UserAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            // "Bearer " 로 시작하는 prefix는 JWT 스팩임.
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    // role keys
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Long userId = Long.parseLong(decodedJWT.getClaim("userId").asString());
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UserClaimDto buildUser = UserClaimDto.builder()
                            .id(userId)
                            .username(username)
                            .roleKeys(Arrays.asList(roles))
                            .build();
                    // 아래 username 파라미터 그냥 object임.
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(buildUser, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) { // 토큰이 유효하지 않을 때
                    log.error("{}", e.getMessage());
                    response.setHeader("error", e.getMessage());
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    Map<String, String> errors = new HashMap<>();
                    errors.put("error_message", e.getMessage());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), errors);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
