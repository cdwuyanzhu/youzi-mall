package com.crms.service;

import com.crms.pojo.vo.CustomerLinkMan;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bruce
 * @since 2022-12-26
 */
public interface ICustomerLinkManService extends IService<CustomerLinkMan> {
    /**
     * 查询6个月内总客户数
     *
     * @return
     */
    int findSixTotal();
}
