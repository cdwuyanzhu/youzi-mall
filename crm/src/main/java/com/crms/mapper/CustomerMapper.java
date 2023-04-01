package com.crms.mapper;

import com.crms.pojo.vo.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bruce
 * @since 2022-12-24
 */
@Repository
public interface CustomerMapper extends BaseMapper<Customer> {
    /**
     * 查询一个月内总客户数
     *
     * @return
     */
    @Select("select count(*) FROM customer where create_date between date_sub(now(),interval 1 month) and now()")
    int findOneTotal();
    /**
     * 查询二个月内总客户数
     *
     * @return
     */
    @Select("select count(*) FROM customer where create_date between date_sub(now(),interval 2 month) and now()")
    int findTwoTotal();


    /**
     * 查询3个月内总客户数
     *
     * @return
     */
    @Select("select count(*) FROM customer where create_date between date_sub(now(),interval 3 month) and now()")
    int findThreeTotal();

    /**
     * 查询4个月内总客户数
     *
     * @return
     */
    @Select("select count(*) FROM customer where create_date between date_sub(now(),interval 4 month) and now()")
    int findFourTotal();

    /**
     * 查询5个月内总客户数
     *
     * @return
     */
    @Select("select count(*) FROM customer where create_date between date_sub(now(),interval 5 month) and now()")
    int findFiveTotal();

    /**
     * 查询6个月内总客户数
     *
     * @return
     */

    @Select("select count(*) FROM customer where create_date between date_sub(now(),interval 6 month) and now()")
    int findSixTotal();
}
