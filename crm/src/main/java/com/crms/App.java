package com.crms;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@MapperScan("com.crms.mapper")
@EnableSwagger2Doc
@EnableAspectJAutoProxy(proxyTargetClass = false) //开启AspectJ注解
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
        System.out.println("crm.System启动成功");
    }
    @Bean("bCryptPasswordEncoder")
    public PasswordEncoder BCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
