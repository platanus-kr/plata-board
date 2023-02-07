package org.platanus.webboard.config.security;

import lombok.RequiredArgsConstructor;
import org.platanus.webboard.config.constant.ConfigConstant;
import org.platanus.webboard.domain.UserRole;
import org.springframework.beans.factory.annotation.Value;
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

/**
 * Plata Board Spring Security Configuration
 */
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationConfiguration authConfig;

    @Value("${plataboard.environment.profile}")
    private String profile;

    /**
     * WebSecurityConfigurerAdapter의 deprecated로 인한 AuthenticationManager Bean 주입. <br />
     * 바로 위의 AuthenticationConfiguration를 먼저 DI 해야한다. <br />
     * 출처 : <a href="http://disq.us/p/2oztbd7">reply in Spring Security without the WebSecurityConfigurerAdapter</a>
     *
     * @param authConfig
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * SecurityFilterChain Bean
     *
     * @param http
     * @return
     * @throws Exception
     */
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
        // Permit Swagger
        if (!profile.equals(ConfigConstant.PROPERTY_ENV_PROFILE_PRODUCTION)) {
            http.authorizeRequests()
                    .antMatchers("/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-ui/**", "/swagger-ui/**")
                    .permitAll();
        }
        http.authorizeRequests().antMatchers("/api/migrate/**").hasAnyAuthority(UserRole.ROLE_ADMIN.getKey());
        http.authorizeRequests().antMatchers("/admin/**").denyAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authFilter);
        http.addFilterBefore(new UserAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class); // successHandler..
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/h2-console/**", "/media/**");
    }

}
