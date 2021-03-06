package com.rekik.lostandfound.security;

import com.rekik.lostandfound.repository.AppUserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private SSUserDetailsService userDetailsService;
    @Autowired
    private AppUserRepo appUserRepo;

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUserDetailsService(appUserRepo);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http
                .authorizeRequests()

                //allow to all
                .antMatchers("/", "/imgriri/**",  "/test/**","/displayuserlist","/h2-console/**","/register","/css/**",
                        "/sass/**","/js/**","/img/**","/fonts/**","/landing/**","/bootstrap3/**","/searchbycategory").permitAll()
                //allowed only to recruiter
                .antMatchers("/listlosts","/addlost", "/lostitem/**", "/edititem/**", "/deleteitem/**").hasAuthority("ADMIN")
                //allowed to User and Admin
                //.antMatchers("/useraddlost").access("hasAuthority('ADMIN') or hasAuthority('USER')")
                .antMatchers("/useraddlost"). hasAuthority("USER")

                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(
                        new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll().permitAll()
                .and()
                .httpBasic();

        //For H2
        http
                .csrf().disable();
        http
                .headers().frameOptions().disable();



    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception{
        auth.inMemoryAuthentication()
                .withUser("admin").password("password").authorities("ADMIN");
        auth
                .userDetailsService(userDetailsServiceBean());
    }

}
