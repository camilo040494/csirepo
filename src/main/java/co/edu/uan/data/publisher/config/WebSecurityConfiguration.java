package co.edu.uan.data.publisher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final String LOGIN_URL = null;
private UserDetailsService userDetailsService;
  
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
        "/swagger-ui.html", "/data-publisher/**", "/swagger/ui/index", "/webjars/**", "/data-publisher/");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests().antMatchers("/v2/api-docs", "/swagger-resources", "/swagger-resources/configuration/ui",
//        "/swagger-ui.html", "/swagger-resources/configuration/security", "/data-publisher/").permitAll();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	.cors().and()
	.csrf().disable()
	.authorizeRequests().antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
	.anyRequest().authenticated().and()
		.addFilter(new JWTAuthenticationFilter(authenticationManager()))
		.addFilter(new JWTAuthorizationFilter(authenticationManager()));
  }
  
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
	  return new BCryptPasswordEncoder();
  }
  
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
	  // Se define la clase que recupera los usuarios y el algoritmo para procesar las passwords
	  auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
	  final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	  source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	  return source;
  }
}