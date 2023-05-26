package com.example.userms.filtre;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class jwtAuthFilter  extends UsernamePasswordAuthenticationFilter {
   private AuthenticationManager authenticationManager;

    public jwtAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("attemptAuthentication");
        String email ="test";
        String password = "test";
        email=request.getParameter("email");
        password=request.getParameter("password");
        System.out.println(email);
        System.out.println(password);
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(email,password);


        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("Successful Authentication");
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm=Algorithm.HMAC256("mySecret123");
        String jwtAccessToken= JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(request.getRequestURI().toString())
                .withClaim("roles",user.getAuthorities()
                        .stream().map(ga->ga.getAuthority())
                        .collect(Collectors.toList()))
                .sign(algorithm);
        response.setHeader("Authorization",jwtAccessToken);
    }
}
