package com.company.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("oauth2authSuccessHandler")
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    @Qualifier("oauth2LogoutSuccessHandler")
    private LogoutSuccessHandler logoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers(Url.JWT_VALIDATE.getUrl(), Url.LOGOUT_SUCCESS.getUrl()).permitAll()
                .anyRequest().authenticated()
                .and().oauth2Login().successHandler(authenticationSuccessHandler)
                .and().logout().logoutUrl(Url.LOGOUT.getUrl())
                .logoutSuccessHandler(logoutSuccessHandler).deleteCookies("JSESSIONID", "KSESSION")
                .invalidateHttpSession(true).clearAuthentication(true);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }
}
