package com.example.FirstSecurityApp.config;

import com.example.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
//!!!!Аннотация(снизу) если используем аннотацию @PreAuthorize!!!!
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfiguration(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    //Конфигурируем сам SPRING SECURITY и Авторизацию
    protected void configure(HttpSecurity http) throws Exception {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ПРАВИЛА РАБОТАЮТ СВЕРХУ ВНИЗ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        http
                //.csrf().disable()//Отключаем защиту меж сайтовой подделки запросов CSRF (!!!Добавляем CSRF в шаблон Thymeleaf (!!!только в поле логин!!!))
                .authorizeRequests()//Настройка авторизации
                .antMatchers("/admin").hasRole("ADMIN")// На адрес /админ пускать только с ролью АДМИН(!!!!если не используем аннотацию @PreAuthorize!!!!)
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll()//Адрес куда пускаем всех не аутентифицированных пользователей
                .anyRequest().hasAnyRole("USER", "ADMIN")
                //.anyRequest().authenticated()//Для всех других запросов отправляем пользователей на аутентификацию(!!!!если нет ролей!!!!)
                .and()//И
                .formLogin()//Настройка формы
                .loginPage("/auth/login")//Адрес метода в контроллере
                .loginProcessingUrl("/process_login")//Адрес куда отправлять данные с формы
                .defaultSuccessUrl("/hello", true)//Адрес после успешной аутентификации, true обязательно
                .failureUrl("/auth/login?error")//Адрес в случае не успешной аутентификации
                .and()
                .logout().logoutUrl("/logout")//Адрес для разлогинивания
                .logoutSuccessUrl("/auth/login");//Адрес в случае успешного разлогинивания
    }
    //Настраиваем аутентификацию
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());//Для сравнения шифрованного поля при входе
    }

    //Метод кодировки пароля
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
