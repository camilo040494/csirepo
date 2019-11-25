package co.edu.uan.data.publisher.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
        "/swagger-ui.html", "/upload/**", "/swagger/ui/index", "/webjars/**", "/upload/");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/v2/api-docs", "/swagger-resources", "/swagger-resources/configuration/ui",
        "/swagger-ui.html", "/swagger-resources/configuration/security", "/upload/").permitAll();
  }

}