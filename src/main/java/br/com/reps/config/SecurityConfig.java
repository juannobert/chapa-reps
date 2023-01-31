package br.com.reps.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	 AuthenticationManager authenticationManager(HttpSecurity http) 
	  throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	      .userDetailsService(userDetailsService)
	      .passwordEncoder(passwordEncoder)
	      .and()
	      .build();
	}
	
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
        	.anyRequest().permitAll()
        	
        	.and()
        	
        	.formLogin()
        	.loginPage("/auth/login")
        	.defaultSuccessUrl("/home")
        	.usernameParameter("email")
        	.passwordParameter("password")
        	.permitAll()
        	
        	.and()
        	
        	.logout()
        	.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
        	.logoutSuccessUrl("/auth/login")
        	
        	.and()
        	
        	.csrf().disable();

        return http.build();
	}
}
