package com.hms.security;

import com.hms.utils.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenHelper jwtTokenHelper;
    @Autowired
    UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Inside JwtAuthenticationFilter");
        String header = request.getHeader(Constants.AUTHORIZATION);
        String username=null;
        String authToken=null;
        log.info("Request For URL : "+request.getRequestURI());
        if (header != null && header.startsWith(Constants.TOKEN_PREFIX)){
            log.info("Inside if of JwtFilter");
            authToken=header.replace(Constants.TOKEN_PREFIX,"");
            try {
                username= jwtTokenHelper.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                log.info("Error Occurred during getting user name from token");
            } catch (ExpiredJwtException e) {
                log.info("The token is expired and not valid anymore",e);
            } catch (SignatureException e){
                log.error("Authentication Failed. Username or Password not valid.",e);
            } catch(MalformedJwtException e) {
                System.out.println("invalid jwt");
            }
        } else {
            log.warn("Couldn't find Bearer String, will ignore the header");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("inside if for context holder");
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (this.jwtTokenHelper.validateToken(authToken, userDetails)) {
                log.info("inside if after token validate");
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                log.info("Token not validated!!");
            }
        } else {
            log.info("username is null or context is not null");
        }
        log.info("calling filter chain");
        filterChain.doFilter(request, response);
        log.info("after calling filter chain");
    }
}

