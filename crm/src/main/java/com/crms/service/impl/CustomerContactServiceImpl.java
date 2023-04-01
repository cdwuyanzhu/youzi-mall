package com.crms.service.impl;

import com.crms.pojo.vo.CustomerContact;
import com.crms.mapper.CustomerContactMapper;
import com.crms.service.ICustomerContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class CustomerContactServiceImpl extends ServiceImpl<CustomerContactMapper, CustomerContact> implements ICustomerContactService {

}
