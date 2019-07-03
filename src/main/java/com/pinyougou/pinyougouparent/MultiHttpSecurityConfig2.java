package com.pinyougou.pinyougouparent;

import com.pinyougou.pinyougouparent.service.Impl.sellergoodsserviceimpl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration

public class MultiHttpSecurityConfig2 {
//
//
        @Configuration
        @Order(1)
        public static class AdminSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
////        @Autowired
////        private UserDetailsServiceImpl userDetailsServiceImpl;
////        //@Autowired
////        //private UserDetailsService userDetailsService;
////        @Autowired
////        private PasswordEncoder passwordEncoder;
////
////        @Bean
////        public PasswordEncoder passwordEncoder() {
////            return new BCryptPasswordEncoder();   // 使用 BCrypt 加密
////        }
////
////        @Bean
////        public UserDetailsServiceImpl userDetailsServiceImpl() {
////
////            return new UserDetailsServiceImpl();
////        }
////
////        @Bean
////        public AuthenticationProvider authenticationProvider() {
////            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
////            authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
////            authenticationProvider.setPasswordEncoder(passwordEncoder); // 设置密码加密方式
////            return authenticationProvider;
////        }
//
//
//
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http
                        .antMatcher("/admin/**")//多HttpSecurity配置时必须设置这个，除最后一个外，因为不设置的话默认匹配所有，就不会执行到下面的HttpSecurity了
                        .formLogin()
                        .loginPage("/login.html")//登陆界面页面跳转URL
                        .loginProcessingUrl("/admin/login")//登陆界面发起登陆请求的URL
                        .defaultSuccessUrl("/admin/index.html", true).failureUrl("/login.html")

                        //表单登录，permitAll()表示这个不需要验证
                        .and()//Return the SecurityBuilder
                        .logout()
                        .logoutUrl("/loginOut")//登出请求地址
                        .logoutSuccessUrl("/login.html")
                        .and()
                        .authorizeRequests()//启用基于 HttpServletRequest 的访问限制，开始配置哪些URL需要被保护、哪些不需要被保护
                        .antMatchers("/css/**", "/img/**", "/js/**", "/plugins/**", "/login.html", "/**/add/**/", "/shoplogin.html", "mainindex.html").permitAll()//未登陆用户允许的请求
                        .anyRequest().hasAnyRole("ADMIN")//其他/fore路径下的请求全部需要登陆，获得USER角色
                        .and()
                        .headers().frameOptions().sameOrigin()//关闭X-Frame-Options
                        .and()
                        .csrf().disable();

            }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                        .inMemoryAuthentication()
//                        .withUser("admin").password("123456").roles("ADMIN");
//    }
            @Autowired
            public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////            auth.userDetailsService(userDetailsServiceImpl);
////            auth.authenticationProvider(authenticationProvider());
////            auth.eraseCredentials(false);
                auth
                        .inMemoryAuthentication()
                        .withUser("admin").password("123456").roles("ADMIN");



            }
        }
    @Configuration
    @Order(2)
    public static class UserSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
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
        public UserDetailsServiceImpl userDetailsServiceImpl() {

            return new UserDetailsServiceImpl();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
            authenticationProvider.setPasswordEncoder(passwordEncoder); // 设置密码加密方式
            return authenticationProvider;
        }



        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/seller/**")//多HttpSecurity配置时必须设置这个，除最后一个外，因为不设置的话默认匹配所有，就不会执行到下面的HttpSecurity了
                    .formLogin()
                    .loginPage("/shoplogin.html")//登陆界面页面跳转URL
                    .loginProcessingUrl("/seller/login")//登陆界面发起登陆请求的URL
                    .defaultSuccessUrl("/seller/index2.html", true).failureUrl("/shoplogin.html")

                    //表单登录，permitAll()表示这个不需要验证s
                    .and()//Return the SecurityBuilder
                    .logout()
                    .logoutUrl("/loginOut")//登出请求地址
                    .logoutSuccessUrl("/shoplogin.html")
                    .and()
                    .authorizeRequests()//启用基于 HttpServletRequest 的访问限制，开始配置哪些URL需要被保护、哪些不需要被保护
                    .antMatchers("/css/**", "/img/**", "/js/**", "/plugins/**", "/login.html", "/**/add/**/", "/shoplogin.html", "mainindex.html").permitAll()//未登陆用户允许的请求
                    .anyRequest().hasAnyRole("SELLER")//其他/fore路径下的请求全部需要登陆，获得USER角色
                    .and()
                    .headers().frameOptions().sameOrigin()//关闭X-Frame-Options
                    .and()
                    .csrf().disable();

        }

//        @Autowired
//        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//            auth.userDetailsService(userDetailsServiceImpl);
//            auth.authenticationProvider(authenticationProvider());
//            auth.eraseCredentials(false);
//            //auth
//            //  .inMemoryAuthentication()
//            // .withUser("admin2").password("123456").roles("ADMIN","SELLER")
//            //.and()
//            // .w
//
//        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsServiceImpl);
            auth.authenticationProvider(authenticationProvider());
            auth.eraseCredentials(false);
        }

    }
}
