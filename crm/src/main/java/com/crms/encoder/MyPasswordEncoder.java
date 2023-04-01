package com.crms.encoder;
import com.crms.utils.Md5Utils;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * 自定义一个加密算法
 */
//@Component
public class MyPasswordEncoder implements PasswordEncoder {

    /**
     * 加密算法--自定义算法
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        String prefix="34";
        String surfix="56";
        String pwd = Md5Utils.MD5Encode(Md5Utils.MD5Encode(prefix+rawPassword+surfix)+surfix);
        return pwd;
    }

    /**
     * 匹配
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        //区分大小写比较
        return encode(rawPassword).equals(encodedPassword);
    }
}
