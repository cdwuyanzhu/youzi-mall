package com.crms.mapper;

import com.crms.pojo.vo.CustomerOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bruce
 * @since 2022-12-27
 */
@Repository
public interface CustomerOrderMapper extends BaseMapper<CustomerOrder> {
    @Select("select count(*) FROM customer_order where create_date between date_sub(now(),interval 6 month) and now()")
    int findSixTotal();
}
