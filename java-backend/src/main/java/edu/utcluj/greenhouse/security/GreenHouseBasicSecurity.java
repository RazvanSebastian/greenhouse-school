package edu.utcluj.greenhouse.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * 
 * @author Razvan
 *
 * Only one user has the authority to access resources => user : greenhouse and password : strongpassword
 * To be able to access end-points the user must send an additional header:
 * Key : Authorization and Value : greenhouse:strongpassword (encoded to base64)
 */

@Configuration
@EnableWebSecurity
public class GreenHouseBasicSecurity extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("greenhouse").password("strongpassword").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/sensor").hasRole("ADMIN").and().httpBasic();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	
	
}
