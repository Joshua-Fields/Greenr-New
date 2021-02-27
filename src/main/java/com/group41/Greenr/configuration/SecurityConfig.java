package com.group41.Greenr.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.group41.Greenr.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{ // Disables the default auto-configuration and enables our own custom security configuration
	
	// Gives and permits access to different URLs
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.authorizeRequests().antMatchers("/registration**", "/js/**", "/css/**", "/img/**")
		.permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
	
			.permitAll()
			.defaultSuccessUrl("/profile", true)
		.and()
		.logout().permitAll()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout")
		.and()
		.csrf().ignoringAntMatchers("/uploadFile")
		.and()
		.rememberMe()
			.key("privateKey")
			.tokenValiditySeconds(1 * 24 * 60 * 60)
		.userDetailsService(userService)
		;
//		.invalidateHttpSession(true)
//		.clearAuthentication(true)	
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder authenticate) throws Exception{
		authenticate.authenticationProvider(authenticationProvider());
	}
	
	@Autowired
	private UserService userService;
	@Bean
	public BCryptPasswordEncoder passwordEncoder() { // Using BCryptPasswordEncoder to encrypt the password
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticate = new DaoAuthenticationProvider();
		authenticate.setUserDetailsService(userService);
		authenticate.setPasswordEncoder(passwordEncoder()); //Setting password encoder
		return authenticate;
		
	}
	

}
