package com.crms.mapper;

import com.crms.pojo.vo.Permission;
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
public interface PermissionMapper extends BaseMapper<Permission> {
    @Select("SELECT\n" +
            "DISTINCT\tpermission.*\n" +
            "\t\n" +
            "FROM\n" +
            "\tuser\n" +
            "\tINNER JOIN user_role ON `user`.id = user_role.user_id\n" +
            "\tINNER JOIN role ON user_role.role_id = role.id \n" +
            "\tinner JOIN role_permission on role.id=role_permission.role_id\n" +
            "\tinner JOIN permission on role_permission.permission_id=permission.id\n" +
            "\t\n" +
            "WHERE\n" +
            "\t`user`.user_name = #{userName} \n" +
            "\tAND `user`.isdel = 0 \n" +
            "\tAND user_role.isdel = 0 \n" +
            "\tAND role.isdel =0\n" +
            "\tAND permission.isdel =0\n" +
            "\tAND role_permission.isdel =0\n"+
            "\tAND type =0\n")
    public List<Permission> findPermissionList(String userName);
    @Select("select permission.* ,role.role_name from role \n" +
            "inner join role_permission on role.id= role_permission.role_id\n" +
            "inner join permission on role_permission.permission_id=permission.id\n" +
            "where role.isdel=0\n" +
            "and role_permission.isdel=0\n" +
            "and permission.isdel=0\n" +
            "and permission.type=#{type}")
    public List<Permission> findPermissionByType(String type);
    @Select("select * from permission where isdel=0 and type=#{type}")
    public List<Permission> queryPermissionByType(String type);
    @Select("SELECT\n" +
            "DISTINCT\tpermission.*\n" +
            "\t\n" +
            "FROM\n" +
            "\tuser\n" +
            "\tINNER JOIN user_role ON `user`.id = user_role.user_id\n" +
            "\tINNER JOIN role ON user_role.role_id = role.id \n" +
            "\tinner JOIN role_permission on role.id=role_permission.role_id\n" +
            "\tinner JOIN permission on role_permission.permission_id=permission.id\n" +
            "\t\n" +
            "WHERE\n" +
            "\t`user`.user_name = #{userName} \n" +
            "\tAND `user`.isdel = 0 \n" +
            "\tAND user_role.isdel = 0 \n" +
            "\tAND role.isdel =0\n" +
            "\tAND permission.isdel =0\n" +
            "\tAND role_permission.isdel =0\n"+
            "\tAND type =1\n")
    public List<Permission> queryPermissionList(String userName);
}
