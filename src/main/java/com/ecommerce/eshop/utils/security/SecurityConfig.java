package com.ecommerce.eshop.utils.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.ecommerce.eshop.user.UserRole.*;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles(SELLER.name())
                .build();

        UserDetails customer = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles(CUSTOMER.name())
                .build();

        return new InMemoryUserDetailsManager(userDetails,customer);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/products/**").hasRole(CUSTOMER.name())
                .antMatchers(HttpMethod.POST, "/products/**").hasRole(SELLER.name())
                .antMatchers(HttpMethod.PUT,"/products/**").hasRole(SELLER.name())
                .antMatchers(HttpMethod.DELETE, "/products").hasRole(SELLER.name())
                 //TODO: osobne mapowanie dla customera(widzi swoje) i sprzedawcy(widzi wszystkie)
                .antMatchers(HttpMethod.GET, "/orders/**").hasAnyRole(SELLER.name(), CUSTOMER.name())
                .antMatchers(HttpMethod.POST, "/orders/**").hasRole(CUSTOMER.name())
                .antMatchers(HttpMethod.PUT,"/orders/**").hasRole(ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/orders/**").hasRole(ADMIN.name())
                .and()
                .httpBasic()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable();
    }
}
