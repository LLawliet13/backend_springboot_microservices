package com.example.mock2.configuration;

import com.example.mock2.Service.CustomLogoutHandler;
import com.example.mock2.Service.CustomSuccessHandler;
import com.example.mock2.filter.CustomAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    public CustomLogoutHandler customLogoutHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/register", "/login", "/signIn"
//                        ,"/css/**","/js/**","/img/**","/lib/**"
                        , "/useRefreshToken").permitAll()

                .anyRequest().authenticated()
//                .and().formLogin
//                        (
//                                form -> form
//                                        .defaultSuccessUrl("/index")
//                                        .loginPage("/login")
//                                        .failureUrl("/login?error=bad_credential")
//                        )
//
//                .logout()
//                .logoutUrl("/logout")
//                .addLogoutHandler(customLogoutHandler)
//                .logoutSuccessHandler(
////                        new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)
//                       new CustomSuccessHandler()
//                )
//                .permitAll()

//
        ;

        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }


}
