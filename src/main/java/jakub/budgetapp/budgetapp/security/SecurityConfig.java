package jakub.budgetapp.budgetapp.security;

import jakub.budgetapp.budgetapp.security.authenticationHandlers.CustomAuthenticationFailureHandler;
import jakub.budgetapp.budgetapp.security.authenticationHandlers.CustomAuthenticationSuccessHandler;
import jakub.budgetapp.budgetapp.security.authenticationHandlers.CustomLogoutSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                          CustomAuthenticationFailureHandler customAuthenticationFailureHandler,
                          CustomLogoutSuccessHandler customLogoutSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
    }

    // take a look at csrf and cors

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/check").authenticated()
                .antMatchers("/operation-controller/*").authenticated()
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .and()
                .logout()
                .logoutSuccessHandler(customLogoutSuccessHandler);
    }

}
