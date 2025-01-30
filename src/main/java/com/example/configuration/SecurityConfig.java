//package com.example.configuration;
//
//import java.util.Collections;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//
//import com.example.filter.JWTTokenValidationFilter;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@Configuration
//public class SecurityConfig {
//
//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		return http
//				.sessionManagement(sessionConfig->sessionConfig
//						     .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.cors(c->c.configurationSource(new CorsConfigurationSource() 
//				{
//			
//			@Override
//			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//				CorsConfiguration cors = new CorsConfiguration();
//				cors.setAllowCredentials(true);
//				cors.setMaxAge(3600L);
//				cors.setAllowedMethods(Collections.singletonList("*"));
//				cors.setAllowedOrigins(Collections.singletonList("*"));
//				cors.setAllowedHeaders(Collections.singletonList("*"));
//				cors.setExposedHeaders(Collections.singletonList("Authorization"));
//				return cors;
//			}
//		}))
//				.csrf(c->c.disable())
//				.addFilterBefore(new JWTTokenValidationFilter(), BasicAuthenticationFilter.class)
//				.authorizeHttpRequests(r->
//				      r
//				      .requestMatchers("/html").permitAll()
//				      .requestMatchers("/user/register").permitAll()
//				       .requestMatchers("/user/login").permitAll()
//				            
//				       .requestMatchers(HttpMethod.GET,"/bill/**").hasAnyRole("ACCOUNTANT","SALES")
//				       .requestMatchers(HttpMethod.GET,"/payroll/**").hasAnyRole("ACCOUNTANT","HR")
//
//				       .requestMatchers("/user/**").hasRole("ADMIN") 
//				       .requestMatchers("/customer/**").hasRole("SALES") 
//				       .requestMatchers("/bill/**").hasRole("SALES")
//				       .requestMatchers("/payroll/**").hasRole("HR")
//				            
//				       .anyRequest().authenticated())
//				.build();
//	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//	    return authenticationConfiguration.getAuthenticationManager();
//	}
//}

package com.example.configuration;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.filter.JWTTokenValidationFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(sessionConfig -> sessionConfig
                             .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(c -> c.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration cors = new CorsConfiguration();
                        cors.setAllowCredentials(true);
                        cors.setMaxAge(3600L);
                        cors.setAllowedMethods(Collections.singletonList("*"));
                        cors.setAllowedOrigins(Collections.singletonList("*"));
                        cors.setAllowedHeaders(Collections.singletonList("*"));
                        cors.setExposedHeaders(Collections.singletonList("Authorization"));
                        return cors;
                    }
                }))
                .csrf(c -> c.disable()) // Disable CSRF for stateless APIs
                .addFilterBefore(new JWTTokenValidationFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(r ->
                      r
                      .requestMatchers("/html").permitAll() // Allow public access to HTML pages
                      .requestMatchers("/user/register").permitAll() // Allow registration
                      .requestMatchers("/user/login").permitAll() // Allow login
                      
                      .requestMatchers(HttpMethod.GET, "/bill/**").hasAnyRole("ACCOUNTANT", "SALES") // Bill access
                      .requestMatchers(HttpMethod.GET, "/payroll/**").hasAnyRole("ACCOUNTANT", "HR") // Payroll access
                      .requestMatchers("/user/**").hasRole("ADMIN") // Admin-specific actions
                      .requestMatchers("/customer/**").hasRole("SALES") // Sales-specific actions
                      .requestMatchers("/bill/**").hasRole("SALES") // Bill management by sales
                      .requestMatchers("/payroll/**").hasRole("HR") // HR payroll management
                      .anyRequest().authenticated() // Any other request requires authentication
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

