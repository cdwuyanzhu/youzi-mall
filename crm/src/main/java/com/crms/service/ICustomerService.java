package com.crms.service;

import com.crms.pojo.vo.Customer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bruce
 * @since 2022-12-24
 */
public interface ICustomerService extends IService<Customer> {
    /**
     * 查询一个月内总客户数
     *
     * @return
     */
    int findOneTotal();
    /**
     * 查询二个月内总客户数
     *
     * @return
     */
    int findTwoTotal();


    /**
     * 查询3个月内总客户数
     *
     * @return
     */
    int findThreeTotal();

    /**
     * 查询4个月内总客户数
     *
     * @return
     */
    int findFourTotal();

    /**
     * 查询5个月内总客户数
     *
     * @return
     */
    int findFiveTotal();

    /**
     * 查询6个月内总客户数
     *
     * @return
     */
    int findSixTotal();

}
