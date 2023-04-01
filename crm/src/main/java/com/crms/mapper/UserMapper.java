package com.crms.mapper;

import com.crms.pojo.vo.User;
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
 * @since 2022-12-28
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
   @Select("SELECT `user`.* FROM `user` INNER JOIN user_role on user.id= user_role.user_id WHERE user_role.role_id=#{roleid}")
   public List<User> getUserList(Integer roleid);
}
