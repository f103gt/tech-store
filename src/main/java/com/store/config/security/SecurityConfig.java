package com.store.config.security;

import com.store.dao.model.users.RoleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    /*@Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails user = User.withUsername("manager")
                .roles("manager")
                .build();
        return  new InMemoryUserDetailsManager(user);
    }*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizationManagerRequestMatcher ->{
                    authorizationManagerRequestMatcher.requestMatchers("/").permitAll();
                    authorizationManagerRequestMatcher.requestMatchers("/user").hasRole(RoleEnum.USER.getRole());
                    authorizationManagerRequestMatcher.requestMatchers("/admin").hasRole("ADMIN");
                })
                .httpBasic(Customizer.withDefaults())
                .build();                              //authentication provider
    }
}