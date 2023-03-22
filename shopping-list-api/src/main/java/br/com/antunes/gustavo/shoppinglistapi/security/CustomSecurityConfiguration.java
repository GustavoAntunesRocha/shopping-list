package br.com.antunes.gustavo.shoppinglistapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfiguration {
	

	@Bean
	SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
				
		http
			.cors().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.formLogin().disable()
			.securityMatcher("/**")
			.authorizeHttpRequests(registry -> registry
					.requestMatchers("/**").permitAll()
					.anyRequest().authenticated()
					
			);
		
		http.headers((headers) -> headers.frameOptions().sameOrigin());
		return http.build();
	}
	
}
