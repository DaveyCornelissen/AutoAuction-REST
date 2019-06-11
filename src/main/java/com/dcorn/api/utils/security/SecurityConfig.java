package com.dcorn.api.utils.security;

import com.dcorn.api.authentication.IAuthenticationService;
import com.dcorn.api.utils.handler.AuthenticationErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.dcorn.api.utils.security.SecurityConstants.*;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private IAuthenticationService service;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(IAuthenticationService service, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.service = service;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(new AuthenticationErrorHandler())
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, AUTH_URL_SIGNUP).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .addFilter(jwtAuthorizationFilter())

                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * To enable endpoints for SwaggerUI
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**");

    }

    /**
     * To change the /login Url of the API
     * @return jwtAuthenticationFilter
     * @throws Exception
     */
    private JwtAuthenticationFilter jwtAuthorizationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        jwtAuthenticationFilter.setFilterProcessesUrl(AUTH_URL_SIGNIN);
        return jwtAuthenticationFilter;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
