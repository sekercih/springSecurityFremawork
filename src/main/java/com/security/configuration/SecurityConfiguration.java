package com.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // WebSecurityConfigurerAdapter içerisindeki HTTP securty metodunu override ediyoruz.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().permitAll(); // şifreleri devre dışı bıraktık.
        http.csrf().disable().
                authorizeRequests().    // İstekleri denetle

                antMatchers("/", "index", "/css/*", "/js/*").permitAll().
                //===============ROKE-BASED AUTOHENTICATION======================
                        //==USER rolune sahip kullanıcının erişebileceği pathin tanımlanması==
                        antMatchers("/kisiler").hasRole(KisiRole.USER.name()).
                //   //==ADMIN rolune sahip kullanıcının erişebileceği pathin tanımlanması==
                antMatchers("/kisiler/ekle").hasRole(KisiRole.ADMIN.name()).
                anyRequest().           // tum istekleri
                authenticated().        // Şifreli olarak kullan
                and().                  // VE farklı işmleri birleştirmek için
                formLogin().            // form login sayfasi giriş yapılsin
                and().                  // VE farklı işmleri birleştirmek için
                httpBasic();           // basic http kimlik denetimini kullan
        //http.csrf().disable(); // Cross-Site-Request-Forgery disable yaptık


    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder().username("user").
                password(passwordEncoder.encode("1234")).authorities(KisiRole.USER.name()).build();
        //roles("USER").build();

        UserDetails admin1 = User.builder().username("admin")
                .password(passwordEncoder.encode("4321")).authorities(KisiRole.ADMIN.name()).build();
        //roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user1, admin1);


    }
}
