package com.crms.service;

import com.crms.pojo.vo.CustomerOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bruce
 * @since 2022-12-27
 */
public interface ICustomerOrderService extends IService<CustomerOrder> {
    /**
     * 查询6个月内总客户数
     *
     * @return
     */
    int findSixTotal();
}
