package com.crms.config;
import com.crms.filter.CheckCodeFilter;
import com.crms.handler.LoginFailureHandler;
import com.crms.handler.LoginSuccessHandler;
import com.crms.pojo.vo.Permission;
import com.crms.service.IPermissionService;
import com.crms.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true,jsr250Enabled = true,securedEnabled = true)
@Slf4j
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    LoginFailureHandler loginFailureHandler;
    @Autowired
    IUserService userService;
    @Autowired
    CheckCodeFilter checkCodeFilter;
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    IPermissionService iPermissionService;
    /**
     * 认证的数据源
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //模拟一个认证账号和密码
//        auth.inMemoryAuthentication()
//                .withUser("zhangsan")
//                .password("{noop}123")  //  {noop} 表示密码是明文 不加密
//                .roles("USER");  //当前角色: USER
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * 认证规则
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        //支持项目中的框架frame布局,同源: 协议相同http:  IP相同 127.0.0.1  端口相同80
        http.headers().frameOptions().sameOrigin();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
                expressionInterceptUrlRegistry = http.authorizeRequests()  //对请求做授权处理
                .antMatchers("/system/code", "/login.html", "/403.html", "/404.html", "/500.html", "/login.html", "/css/**", "/img/**", "/js/**", "/layui/**")
                .permitAll();

        //动态添加授权:从数据库动态查询出，哪些资源需要什么样的权限 查询数据库，访问地址需要什么角色
//        List<Permission> permissions = iPermissionService.findPermissionByType("1");
//        for (Permission permission : permissions) {
//            log.info(permission.getUrl() + "----->" + permission.getRoleName());
//            expressionInterceptUrlRegistry.antMatchers(permission.getUrl()).hasRole(permission.getRoleName());
//        }
        //动态添加授权:从数据库动态查询出，哪些资源需要什么样的权限 查询数据库，访问地址需要权限
        List<Permission> permissions = iPermissionService.queryPermissionByType("1");
        for (Permission permission : permissions) {
            log.info("访问"+permission.getUrl() + "----->需要权限：" + permission.getPermissionName());
            expressionInterceptUrlRegistry.antMatchers(permission.getUrl()).hasAuthority(permission.getPermissionName());
        }
        expressionInterceptUrlRegistry
                .anyRequest().authenticated() //其他路径都要拦截
                .and().formLogin()  //允许表单登录， 设置登陆页
                .loginPage("/login.html") //如果没有认证，跳转到自定义的登录页面
                .loginProcessingUrl("/login") //登录的后台控制器地址,注解版手动配置
                .successHandler(loginSuccessHandler) // 登录成功的后台处理处理器
                .failureHandler(loginFailureHandler)  // 登录失败的后台处理处理器
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout") //系统注销的控制器地址
                .invalidateHttpSession(true) //注销成功会话失效
                .logoutSuccessUrl("/login.html") //注销成功跳转地址
                .permitAll()
                .and()
                .csrf()
                .disable(); //关闭CSRF请求伪造攻击防御


//        //支持项目中的框架frame布局，同源策略：协议相同，IP地址相同，端口相同
//        http.headers().frameOptions().sameOrigin();
//        http.authorizeRequests()
//                .antMatchers("/system/code","/403.html", "/404.html", "/500.html", "/login.html", "/css/**", "/img/**", "/js/**", "/layui/**")
//                .permitAll()
//                .antMatchers("/**")
//                .hasAnyRole("超级管理员,营销部,市场部,售后部")
//                .anyRequest()
//                .authenticated()
//                .and()
//                .addFilterBefore(checkCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin()
//                .loginPage("/login.html") //如果没有认证，跳转到自定义的登录页面
//                .loginProcessingUrl("/login") //登录的后台控制器地址
//                .usernameParameter("username") //表单账号参数 默认值 username
//                .passwordParameter("password") //表单账号参数 默认值 password
//                .successHandler(loginSuccessHandler) // 登录成功的后台处理处理器
//                .failureHandler(loginFailureHandler)  // 登录失败的后台处理处理器
//                //.successForwardUrl("/home.jsp") //登录成功跳转地址
//                //.failureForwardUrl("/failure.jsp") //登录失败跳转地址
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout") //系统注销的控制器地址
//                .invalidateHttpSession(true) //注销成功会话失效
//                .logoutSuccessUrl("/login.html") //注销成功跳转地址
//                .permitAll()
//                .and()
//                .csrf()
//                .disable(); //关闭CSRF请求伪造攻击防御
    }

}
