package com.crms.service;

import com.crms.pojo.vo.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bruce
 * @since 2022-12-30
 */
public interface IPermissionService extends IService<Permission> {
    public List<Permission> findPermissionList(String userName);
    public List<Permission> findPermissionByType(String type);
    public List<Permission> queryPermissionByType(String type);
    public List<Permission> queryPermissionList(String userName);
}
