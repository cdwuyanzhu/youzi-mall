package com.crms.service.impl;

import com.crms.pojo.vo.CustomerOrder;
import com.crms.mapper.CustomerOrderMapper;
import com.crms.service.ICustomerOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bruce
 * @since 2022-12-27
 */
@Service
public class CustomerOrderServiceImpl extends ServiceImpl<CustomerOrderMapper, CustomerOrder> implements ICustomerOrderService {
 @Autowired
  CustomerOrderMapper customerOrderMapper;
    @Override
    public int findSixTotal() {
        return customerOrderMapper.findSixTotal();
    }
}
