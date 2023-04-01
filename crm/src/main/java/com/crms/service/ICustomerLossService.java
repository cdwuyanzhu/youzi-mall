package com.crms.service;

import com.crms.pojo.vo.CustomerLoss;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bruce
 * @since 2022-12-27
 */
public interface ICustomerLossService extends IService<CustomerLoss> {
   public void customerLoss(CustomerLoss customerLoss);
   /**
    * 查询6个月内总客户数
    *
    * @return
    */
   int findSixTotal();
}
