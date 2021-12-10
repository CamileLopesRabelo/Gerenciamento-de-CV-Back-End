package com.dbc.curriculocv.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and().cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/pokemon/**", "/habilidade/**", "/tipo/**", "/evolucao/**", "/pokemon/dados/**").hasRole("AUXILIAR")
                .antMatchers(HttpMethod.PUT, "/pokemon/**", "/habilidade/**", "/tipo/**", "/evolucao/**").hasRole("AUXILIAR")
                .antMatchers(HttpMethod.PUT, "/usuario/update-password/**").hasRole("TREINADOR")
                .antMatchers(HttpMethod.GET, "/pokemon/**", "/habilidade/**", "/tipo/**", "/evolucao/**", "/pokemon/dados/**").hasRole("TREINADOR")
                .antMatchers("/**").hasRole("PROFESSOR_CARVALHO")
                .anyRequest().authenticated()
                .and().addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-ui/**",
                "/v2/api-docs",
                "/swagger-ui.html",
                "/swagger-resources/**"
        );
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
