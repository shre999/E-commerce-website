package com.sport.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.sport.service.CustomUserDetailService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests()
                .requestMatchers("/", "/shop/**", "/register", "/h2-console/**")
                .permitAll()
                .requestMatchers("/admin/**")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .permitAll()
                        .failureUrl("/login?error= true")
                        .defaultSuccessUrl("/")
                        .usernameParameter("username")
                        .passwordParameter("password"))
                .exceptionHandling(withDefaults())
                .csrf(withDefaults());
		http.headers(headers -> headers.frameOptions().disable());
		 http.authenticationProvider(daoAuthenticationProvider());
		 
		 return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		
			DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
			provider.setUserDetailsService(customUserDetailService); 
			provider.setPasswordEncoder(passwordEncoder());
			return provider;
			
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**", "/productImages/**", "/css/**",
				"/js/**");
	}
	
	
}
