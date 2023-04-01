package com.crms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crms.mapper.PermissionMapper;
import com.crms.mapper.RoleMapper;
import com.crms.pojo.vo.Permission;
import com.crms.pojo.vo.Role;
import com.crms.pojo.vo.User;
import com.crms.mapper.UserMapper;
import com.crms.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bruce
 * @since 2022-12-28
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PermissionMapper permissionMapper;
    @Override
    public List<User> getUserList(Integer roleid) {
        List<User> userList = userMapper.getUserList(roleid);
        return userList;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("isdel", 0).eq("user_name", userName));
        if (user!= null) {
//            log.info("数据库认证成功："+userName);
//            ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
//            List<Role> roles = roleMapper.findRoleByUserName(userName);
//            for (Role role : roles) {
//                authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
//            }

            log.info("数据库认证成功："+userName);
            ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
            List<Permission> permissions = permissionMapper.queryPermissionList(userName);
            for (Permission permission : permissions) {
                log.info(userName+"具备的权限有：------->"+permission.getPermissionName());
                authorities.add(new SimpleGrantedAuthority(permission.getPermissionName()));
            }
            return new org.springframework.security.core.userdetails.User
                    (user.getUserName(),
                            user.getUserPwd(),//{noop}表示用明文
                            user.getIsValid()==1,//账户是否可用
                            true,//账户是否过期
                            true,//密码是否过期
                            true,//账户是否被锁定
                            authorities);
        }
        return null;
    }
}
