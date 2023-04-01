package com.crms.service.impl;

import com.crms.pojo.vo.Customer;
import com.crms.mapper.CustomerMapper;
import com.crms.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bruce
 * @since 2022-12-24
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {
    @Autowired
     CustomerMapper customerMapper;
    @Override
    public int findOneTotal() {
        return customerMapper.findOneTotal();
    }

    @Override
    public int findTwoTotal() {
        return customerMapper.findTwoTotal();
    }

    @Override
    public int findThreeTotal() {
        return customerMapper.findThreeTotal();
    }

    @Override
    public int findFourTotal() {
        return customerMapper.findFourTotal();
    }

    @Override
    public int findFiveTotal() {
        return customerMapper.findFiveTotal();
    }

    @Override
    public int findSixTotal() {
        return customerMapper.findSixTotal();
    }
}
