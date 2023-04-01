package com.crms.service.impl;

import com.crms.pojo.vo.Role;
import com.crms.mapper.RoleMapper;
import com.crms.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bruce
 * @since 2022-12-30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    RoleMapper roleMapper;
    @Override
    public List<Role> findRoleByUserName(String userName) {
        return roleMapper.findRoleByUserName(userName);
    }
}
