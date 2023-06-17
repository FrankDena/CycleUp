package it.uniroma3.cu.authentication;

import static it.uniroma3.cu.model.Credentials.ADMIN_ROLE;

import static it.uniroma3.cu.model.Credentials.DEFAULT_ROLE;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;




@Configuration
@EnableWebSecurity
public class AuthConfiguration {
	
	@Autowired
	DataSource datasource;
	

	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
		.csrf().and().cors().disable()
		.authorizeHttpRequests()
		.requestMatchers(HttpMethod.GET, "/**","/index", "/login", "/register", "/servizi","/servizi/**" ,"/servizio", "/reviews", "/review", "/reviews/**").permitAll()
		.requestMatchers(HttpMethod.POST, "/**","/login", "/register").permitAll()
		.requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
		.requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
		.anyRequest().authenticated()
		.and().exceptionHandling().accessDeniedPage("/index")
		.and().formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/success", true)
		.and()
		.logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.clearAuthentication(true).permitAll();
		
		return http.build();
		
	}
	
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication()
		.dataSource(this.datasource)
		.authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
		.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
