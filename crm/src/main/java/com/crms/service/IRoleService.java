package com.crms.service;

import com.crms.pojo.vo.Role;
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
public interface IRoleService extends IService<Role> {
    List<Role> findRoleByUserName(String userName);
}
