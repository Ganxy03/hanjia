# Swagger-Security

+ 为接口请求 添加身份验证

## 依赖
`添加到 pom.yml里`
```pom.yml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>


```

## 配置
```config
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/swagger-ui.html", "/swagger-ui/**").authenticated()
                .antMatchers("/swagger-ui.html", "/swagger-ui/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}123456") // 密码为 "123456"
                .roles("ADMIN");
    }
}


```