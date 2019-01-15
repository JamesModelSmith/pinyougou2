package com.pinyougou.pinyougouparent;

import com.pinyougou.pinyougouparent.service.Impl.sellergoodsserviceimpl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法安全设置

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    //@Autowired
    //private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();   // 使用 BCrypt 加密
    }
    @Bean
    public UserDetailsServiceImpl userDetailsServiceImpl(){

        return new UserDetailsServiceImpl();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        authenticationProvider.setPasswordEncoder(passwordEncoder); // 设置密码加密方式
        return authenticationProvider;
    }
   // @Override
  //protected void configure(AuthenticationManagerBuilder auth) throws Exception{

      // auth
             //   .inMemoryAuthentication()
             //   .withUser("admin2").password("123456").roles("ADMIN","SELLER")
              // .and()
              // .withUser("test").password("test123").roles("SELLER");
  // }


     @Override
     protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().antMatchers("/css/**","/img/**","/js/**","/plugins/**","/login.html","/**/add/**/","/shoplogin.html").permitAll()
                //.antMatchers("/h2-console/**").permitAll()
                //.anyRequest().authenticated()//
                //.antMatchers("/index.html","/brand.html").hasRole("ADMIN")
               //.antMatchers("/index.html").hasRole("SELLER")// 需要相应的角色才能访问// 都可以访问.antMatchers("/**").hasRole("ADMIN")//当前用户必须拥有ROLE_USER的角色才可以访问根目录以及所属子目录的资源
        .and()
        .formLogin()
        .loginPage("/login.html").loginPage("/shoplogin.html")
                .loginProcessingUrl("/login").defaultSuccessUrl("/index.html",true).failureUrl("/login.html")
                //.loginPage("/shoplogin.html").loginProcessingUrl("/login2").defaultSuccessUrl("/index2.html",true).failureUrl("/shoplogin.html")
                //.and().rememberMe().key(KEY) // 启用 remember me

        .and().exceptionHandling().accessDeniedPage("/403");  // 处理异常，拒绝访问就重定向到 403 页面
        http.headers().frameOptions().sameOrigin();
         //http.csrf().ignoringAntMatchers("/h2-console/**"); // 禁用 H2 控制台的 CSRF 防护
         http.csrf().disable();
       // http.formLogin().loginPage("/shoplogin.html").loginProcessingUrl("/login").defaultSuccessUrl("/index.html",true).failureUrl("/shoplogin.html").and().exceptionHandling().accessDeniedPage("/403");
         //http.headers().frameOptions().sameOrigin(); // 允许来自同一来源的H2 控制台的请求
         http.logout();

      }

      /** @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("123456").roles("ADMIN")
                .and()
                .withUser("sunwukong").password("dasheng").roles("ADMIN");

    }**/

    /**
     * 认证信息管理
     * @param auth
     * @throws Exception
     */
    @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl);
        auth.authenticationProvider(authenticationProvider());
        auth.eraseCredentials(false);
        //auth
        //  .inMemoryAuthentication()
         // .withUser("admin2").password("123456").roles("ADMIN","SELLER")
          //.and()
         // .withUser("test").password("test123").roles("SELLER");
    }

}
