package com.crms.service.impl;

import com.crms.pojo.vo.CusService;
import com.crms.mapper.CusServiceMapper;
import com.crms.service.ICusServiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bruce
 * @since 2022-12-29
 */
@Service
public class CusServiceServiceImpl extends ServiceImpl<CusServiceMapper, CusService> implements ICusServiceService {
}
