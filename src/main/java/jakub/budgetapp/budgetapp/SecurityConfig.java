package jakub.budgetapp.budgetapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // take a look at csrf and cors

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/check").authenticated()
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
//                .successHandler()
//                .failureHandler()
//                .defaultSuccessUrl("/loginworks")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/succesfullylogout");
    }
}
