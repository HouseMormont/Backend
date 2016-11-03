package ro.ubbcluj.cs.mormont.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
 ****************************************
 * Created by Tirla Alin on 28.12.2015. *
 ****************************************
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @Value("${spring.datasource.driverClassName}")
    private String datasourceDriverClassName;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/demo").permitAll();
//        http.authorizeRequests().antMatchers("/**").authenticated();
//        http.formLogin()
//                .loginPage("/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .permitAll();
//
//        http.logout()
//                .logoutUrl("/logout")
//                .permitAll();
//
        http.csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, JdbcTemplate jdbcTemplate) throws Exception {
        //TODO authentication should be more generic
        auth.jdbcAuthentication().dataSource(jdbcTemplate.getDataSource())
                .usersByUsernameQuery("select username, password, 1 as enabled from mormont.users where username=?")
                .authoritiesByUsernameQuery("select ? as username, \"ROLE_ADMIN\" as authority");
    }

    @Bean
    public JdbcTemplate dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(datasourceUrl);
        ds.setUsername(datasourceUsername);
        ds.setPassword(datasourcePassword);
        ds.setDriverClassName(datasourceDriverClassName);
        return new JdbcTemplate(ds);
    }
}
