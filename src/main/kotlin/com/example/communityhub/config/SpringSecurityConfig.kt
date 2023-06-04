package com.example.communityhub.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SpringSecurityConfig {
	@Bean
	fun filterChain(http: HttpSecurity): SecurityFilterChain {
		return http
			.csrf { it.disable() }
			.cors { it.disable() }
			.authorizeHttpRequests { it.anyRequest().permitAll() }
			.build()
	}
}
