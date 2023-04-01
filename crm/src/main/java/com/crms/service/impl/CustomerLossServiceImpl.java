package com.crms.service.impl;

import com.crms.mapper.CustomerMapper;
import com.crms.pojo.vo.Customer;
import com.crms.pojo.vo.CustomerLoss;
import com.crms.mapper.CustomerLossMapper;
import com.crms.service.ICustomerLossService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bruce
 * @since 2022-12-27
 */
@Service
public class CustomerLossServiceImpl extends ServiceImpl<CustomerLossMapper, CustomerLoss> implements ICustomerLossService {
    @Autowired
    CustomerLossMapper customerLossMapper;
    @Autowired
    CustomerMapper customerMapper;
    @Override
    @Transactional
    public void customerLoss(CustomerLoss customerLoss) {
        Customer customer = new Customer();
        customer.setId(customerLoss.getCusId());
        customer.setState(1);
        customerMapper.deleteById(customer);
        customerLossMapper.insert(customerLoss);
    }

    @Override
    public int findSixTotal() {
        return customerLossMapper.findSixTotal();
    }
}
