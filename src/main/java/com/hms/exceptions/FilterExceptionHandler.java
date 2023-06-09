package com.hms.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class FilterExceptionHandler extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("inside filter exception handler---------------------------");
            filterChain.doFilter(request,response);
            log.info("leaving filter exception handler-----------------------------");
        } catch (UsernameNotFoundException e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND,e.getMessage());
            log.info("user not found exception handler.................");
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,e.getMessage());
            log.info("error-----------"+e.getMessage());
        }
    }
}
