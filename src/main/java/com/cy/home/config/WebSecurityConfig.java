package com.cy.home.config;

import com.cy.home.domain.DTO.ResponseDTO;
import com.cy.home.util.ResposeFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<Object>() {
                    @Override
                    public <O> O postProcess(O o) {
                        return null;
                    }}
               )
                .and()
                    .formLogin()
                    .loginPage("login_page")
                    .loginProcessingUrl("login")
                    .usernameParameter("user")
                    .passwordParameter("pass")
                    .failureHandler(new AuthenticationFailureHandler() {
                        @Override
                        public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                                            HttpServletResponse httpServletResponse,
                                                            AuthenticationException e) throws IOException, ServletException {
                            httpServletResponse.setContentType("application/json;charset=utf-8");
                            ResponseDTO responseDTO = null;
                            if (e instanceof BadCredentialsException) {
                                responseDTO = ResposeFactory.failed("用户名或者密码错误！",e.getMessage());
                            } else if (e instanceof CredentialsExpiredException) {
                                responseDTO = ResposeFactory.failed("密码过期！",e.getMessage());
                            } else {
                                responseDTO = ResposeFactory.failed("登录失败！");
                            }
                            ObjectMapper objectMapper = new ObjectMapper();
                            PrintWriter out = httpServletResponse.getWriter();
                            out.write(objectMapper.writeValueAsString(responseDTO));
                            out.flush();
                            out.close();
                        }
                    })
                    .successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                                            HttpServletResponse httpServletResponse,
                                                            Authentication authentication) throws IOException, ServletException {

                        }
                    })
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/logout").logoutSuccessHandler(new LogoutSuccessHandler() {
                        @Override
                        public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                                    HttpServletResponse httpServletResponse,
                                                    Authentication authentication) throws IOException, ServletException {

                        }
                    })
                    .permitAll()
                .and().csrf().disable()
                    .exceptionHandling()
                    .accessDeniedHandler(new AccessDeniedHandler() {
                        @Override
                        public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        }
                    });

    }
}
