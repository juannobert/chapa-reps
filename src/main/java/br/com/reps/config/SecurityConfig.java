package br.com.reps.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.reps.entities.enums.UserRole;

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
        	.requestMatchers("/auth/cadastro").permitAll() 
        	.requestMatchers(HttpMethod.POST,"/post/ouvidoria/**").hasAnyAuthority(UserRole.ALUNO.toString())
        	.requestMatchers(HttpMethod.POST,"/post/**").hasAnyAuthority(UserRole.GREMISTA.toString())
        	.requestMatchers(HttpMethod.GET,"/post/**").authenticated()
        	.anyRequest().authenticated()
        	
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
	
	@Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**","/h2-console/**");
    }
}
