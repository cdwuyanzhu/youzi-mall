package com.crms.mapper;

import com.crms.pojo.vo.CustomerLoss;
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
public interface CustomerLossMapper extends BaseMapper<CustomerLoss> {
    @Select("select count(*) FROM customer_loss where create_time between date_sub(now(),interval 6 month) and now()")
    int findSixTotal();
}
