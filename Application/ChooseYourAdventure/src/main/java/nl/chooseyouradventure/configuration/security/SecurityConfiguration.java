package nl.chooseyouradventure.configuration.security;

import lombok.AllArgsConstructor;
import nl.chooseyouradventure.security.authentication.DatabaseUserDetailPasswordService;
import nl.chooseyouradventure.security.authentication.DatabaseUserDetailsService;
import nl.chooseyouradventure.security.workfactor.BcCryptWorkFactorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
class SecurityConfiguration {

    private final BcCryptWorkFactorService bcCryptWorkFactorService;
    private final DatabaseUserDetailsService databaseUserDetailsService;
    private final DatabaseUserDetailPasswordService databaseUserDetailPasswordService;

    @Bean
    protected SecurityFilterChain filterchain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/register","/api/login","/api/story","/api/story/**","/api/user","/api/report","/api/report/**","/topic/**","/ws","/ws/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
        ;
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.cors();
        return httpSecurity.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(
                bcCryptWorkFactorService.calculateStrength());
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsPasswordService(
                this.databaseUserDetailPasswordService);
        provider.setUserDetailsService(this.databaseUserDetailsService);
        return provider;
    }

}
