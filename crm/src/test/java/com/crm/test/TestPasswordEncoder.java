package com.crm.test;

import com.crms.pojo.vo.Permission;
import com.crms.utils.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class TestPasswordEncoder {
    @Test
    public void testPasswordEncoder(){
        PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("md5");
        String qw1 = passwordEncoder.encode("admin");
        log.info(qw1);

    }
    @Test
    public void testPasswordEncoder2(){
        String qw1 = Md5Utils.MD5Encode("admin");
        String qw2 = Md5Utils.MD5Encode("admin");
        String qw3 = Md5Utils.MD5Encode("123456");
        log.info("加密之后的密码："+qw1);
        log.info("加密之后的密码："+qw2);
        log.info("加密之后的密码："+qw3);

    }
    @Test
    public void testPasswordEncoder3(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String qw1 = passwordEncoder.encode("admin");
        String qw2 = passwordEncoder.encode("admin");
        String qw3= passwordEncoder.encode("admin");
        log.info("加密之后的密码："+qw1);
        log.info("加密之后的密码："+qw2);
        log.info("加密之后的密码："+qw3);
        boolean admin = passwordEncoder.matches("admin", "$2a$10$4/81v7fHsySKNkt5aZmI0O9D03C2xQ89CAqqprMsjhvzrxR/KEE.G");
        boolean admin1 = passwordEncoder.matches("admin", "$2a$10$MkpKk29M4gOK9gYvac/l9uZMOOBvt/kKUG36SR9PuVIxMh/PT97w.");
        boolean admin2= passwordEncoder.matches("admin", "$2a$10$YLCGKsBEzSza12NeJ22TUehB3v.e7xreFtSpvg6JDIxGu0rDh.wOm");
        System.out.println(admin+""+admin1+""+admin2);
    }

}
