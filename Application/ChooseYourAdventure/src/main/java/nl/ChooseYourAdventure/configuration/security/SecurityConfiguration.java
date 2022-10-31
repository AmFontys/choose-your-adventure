package nl.ChooseYourAdventure.configuration.security;

import lombok.AllArgsConstructor;
import nl.ChooseYourAdventure.security.authentication.DatabaseUserDetailPasswordService;
import nl.ChooseYourAdventure.security.authentication.DatabaseUserDetailsService;
import nl.ChooseYourAdventure.security.workfactor.BcCryptWorkFactorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final BcCryptWorkFactorService bcCryptWorkFactorService;
    private final DatabaseUserDetailsService databaseUserDetailsService;
    private final DatabaseUserDetailPasswordService databaseUserDetailPasswordService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/register","/api/login","/api/story","/api/user")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.cors();
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
