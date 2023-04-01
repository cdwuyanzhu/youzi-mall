package com.crms.service.impl;

import com.crms.pojo.vo.Permission;
import com.crms.mapper.PermissionMapper;
import com.crms.service.IPermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
     @Autowired
    PermissionMapper permissionMapper;
    @Override
    public List<Permission> findPermissionList(String userName) {

        return permissionMapper.findPermissionList(userName);
    }

    @Override
    public List<Permission> findPermissionByType(String type) {
        return permissionMapper.findPermissionByType(type);
    }

    @Override
    public List<Permission> queryPermissionByType(String type) {
        return permissionMapper.queryPermissionByType(type);
    }

    @Override
    public List<Permission> queryPermissionList(String userName) {
        return permissionMapper.queryPermissionList(userName);
    }
}
