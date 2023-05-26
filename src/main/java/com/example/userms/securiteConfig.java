package com.example.userms;

import com.example.userms.entity.User;
import com.example.userms.filtre.jwtAuthFilter;
import com.example.userms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class securiteConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Override
     protected void configure(AuthenticationManagerBuilder auth) throws Exception {

          auth.userDetailsService(new UserDetailsService() {
              @Override
              public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                  User user = userService.loadUserByemail(email);
                  Collection<GrantedAuthority> authorities=new ArrayList<>();
                  user.getAppRoles().forEach(appRole ->{
                      authorities.add(new SimpleGrantedAuthority(appRole.getRoleName()));
                  } );
                  return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
              }
          });

     }

     @Override
     protected void configure(HttpSecurity http) throws Exception {
      //   http.csrf().disable();
         http.headers().frameOptions().disable();
        // http.formLogin();
         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         http.csrf().disable()
                 .authorizeRequests()
                 .antMatchers(

                         "/api/user",
                         "/api/user/all","/login"




                 ).permitAll();



         ;
          http.authorizeRequests().anyRequest().authenticated();
http.addFilter(new jwtAuthFilter(authenticationManagerBean()));
    }

   @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
