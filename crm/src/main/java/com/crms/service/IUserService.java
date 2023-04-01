package com.crms.service;

import com.crms.pojo.vo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bruce
 * @since 2022-12-28
 */
public interface IUserService extends UserDetailsService, IService<User> {
    public List<User> getUserList(Integer roleid);
}
