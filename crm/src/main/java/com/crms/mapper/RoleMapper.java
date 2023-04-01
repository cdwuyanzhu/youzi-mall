package com.crms.mapper;

import com.crms.pojo.vo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bruce
 * @since 2022-12-30
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    @Select("SELECT role.* from user inner join user_role on user.id=user_role.user_id inner JOIN role on user_role.role_id=role.id WHERE user.user_name=#{userName} and user.isdel=0 and user_role.isdel=0 and role.isdel=0")
    List<Role> findRoleByUserName(String userName);
}
