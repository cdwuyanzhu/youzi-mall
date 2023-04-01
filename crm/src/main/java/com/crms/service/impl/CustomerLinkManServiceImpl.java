package com.crms.service.impl;

import com.crms.pojo.vo.CustomerLinkMan;
import com.crms.mapper.CustomerLinkManMapper;
import com.crms.service.ICustomerLinkManService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bruce
 * @since 2022-12-26
 */
@Service
public class CustomerLinkManServiceImpl extends ServiceImpl<CustomerLinkManMapper, CustomerLinkMan> implements ICustomerLinkManService {
     @Autowired
    CustomerLinkManMapper customerLinkManMapper;
    @Override
    public int findSixTotal() {
        return customerLinkManMapper.findSixTotal();
    }
}
