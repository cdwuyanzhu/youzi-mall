package com.crms.mapper;

import com.crms.pojo.vo.CustomerLinkMan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bruce
 * @since 2022-12-26
 */
@Repository
public interface CustomerLinkManMapper extends BaseMapper<CustomerLinkMan> {
    @Select("select count(*) FROM customer_link_man where create_date between date_sub(now(),interval 6 month) and now()")
    int findSixTotal();
}
