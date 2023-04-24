package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization {
    @Bean
    protected SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/web/index.html").permitAll()
                .antMatchers("/web/assets/scripts/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/login").permitAll()
                .antMatchers(HttpMethod.POST,"/api/logout").permitAll()
                .antMatchers(HttpMethod.POST,"/api/clients").permitAll()
                .antMatchers("/web/admin/**").hasAuthority("ADMIN")
                .antMatchers("/h2-console").hasAuthority("ADMIN")
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers("/web/accounts.html").hasAuthority("CLIENT")
                .antMatchers("/web/account.html").hasAuthority("CLIENT")
                .antMatchers("/web/cards.html").hasAuthority("CLIENT");
        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");
        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");

        http.csrf().disable(); // turn off checking for CSRF tokens
        http.headers().frameOptions().disable();//disabling frameOptions so h2-console can be accessed
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));// if user is not authenticated, just send an authentication failure response
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));// if login is successful, just clear the flags asking for authentication
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));// if login fails, just send an authentication failure response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());// if logout is successful, just send a success response
//        http.cors()
        return http.build();
    }
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}