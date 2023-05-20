package com.example.userms.filtre;

import ch.qos.logback.core.net.server.Client;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.userms.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/api/login")){
            filterChain.doFilter(request,response);
        }else {
            String authorizationHeader=request.getHeader(AUTHORIZATION);
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                try {
                    String token = authorizationHeader.substring(7);
                     Algorithm algorithm=Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT =verifier.verify(token);
                    String email = decodedJWT.getSubject();
                    String x=StringUtils.newStringUtf8 (Base64.decodeBase64(decodedJWT.getPayload()));
                    x=x.substring(1,x.length()-1);
                    System.out.println(Arrays.asList (Arrays.asList(x.split(",")).get(1).split(":")).get(1));
                     String role =Arrays.asList (Arrays.asList(x.split(",")).get(1).split(":")).get(1);
                     role=role.substring(1,role.length()-1);
                     Collection<SimpleGrantedAuthority> authorities =new ArrayList<>();
                     authorities.add(new SimpleGrantedAuthority(role));
                     UsernamePasswordAuthenticationToken authenticationToken
                            =new UsernamePasswordAuthenticationToken(email,null,authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request,response);



                } catch (Exception exception){
                 log.error("error loggin in :{}",exception.getMessage());
                response.setHeader("error",exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                    Map<String,String> error =new HashMap<>();
                    error.put("error_message",exception.getMessage());

                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(),error);
                }

            }else {
                filterChain.doFilter(request,response);
            }
        }
    }
}
