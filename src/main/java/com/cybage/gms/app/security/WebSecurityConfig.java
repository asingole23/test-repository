package com.cybage.gms.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cybage.gms.app.security.jwt.AuthEntryPointJwt;
import com.cybage.gms.app.security.jwt.AuthTokenFilter;
import com.cybage.gms.app.service.UserDetailsServiceImpl;

@Configuration   //loads class beans definations at run time as per service req
@EnableWebSecurity //to access Spring secuity features
@EnableGlobalMethodSecurity(prePostEnabled = true)  // provide acess to override the protected methods for custom implementations
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean  //a method produces a bean to be managed by the Spring container
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {  //configure ->enables http security
		http.cors().and().csrf().disable().exceptionHandling().    //cors->Cross origin Resource Sharing  csrf->Cross Site Request Forgery
		authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().
				sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
				authorizeRequests()
				.antMatchers("/api/auth/**","/feedback/**","/faq/**").permitAll()
				.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
