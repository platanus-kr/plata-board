package org.platanus.webboard.config;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.config.filter.UserAuthenticationFilter;
import org.platanus.webboard.config.filter.UserAuthorizationFilter;
import org.platanus.webboard.domain.UserRole;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationConfiguration authConfig;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        UserAuthenticationFilter authFilter = new UserAuthenticationFilter(authenticationManager(authConfig));
        authFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        // Permit Front Controller
        http.authorizeRequests().antMatchers("/").permitAll();
        // Permit Static Resources
        http.authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
        // Permit Login Controller
        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh").permitAll();
        // Permit Test Controller
        http.authorizeRequests().antMatchers("/api/v1/test/**").permitAll();
        // Check Authority Board Controller
        http.authorizeRequests()
                .antMatchers(GET, "/api/v1/board/**", "/api/v1/article/**", "/api/v1/comment/**")
                .permitAll();
        http.authorizeRequests()
                .antMatchers(POST, "/api/v1/board/**", "/api/v1/article/**", "/api/v1/comment/**")
                .hasAnyAuthority(UserRole.ROLE_USER.getKey());
        // For Legacy Web Page
        http.authorizeRequests().antMatchers("/board/**", "/article/**", "/login/**", "/user/**").permitAll();
        http.authorizeRequests().antMatchers("/api/migrate/**").hasAnyAuthority(UserRole.ROLE_ADMIN.getKey());
        http.authorizeRequests().antMatchers("/admin/**").denyAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authFilter);
        http.addFilterBefore(new UserAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class); // successHandler..
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**");
    }

}
