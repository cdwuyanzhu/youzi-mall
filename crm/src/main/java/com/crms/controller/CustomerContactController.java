package com.crms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crms.pojo.vo.Customer;
import com.crms.pojo.vo.CustomerContact;
import com.crms.service.ICustomerContactService;
import com.crms.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bruce
 * @since 2022-12-26
 */
@RestController
@RequestMapping("/customer-contact/")
@Api(tags = "客户API",description = "提供客户信息相关的Rest API")
@ApiResponses({
        @ApiResponse(code = 200,message = "请求成功"),
        @ApiResponse(code = 500,message = "服务器内部错误"),
        @ApiResponse(code = 404,message = "请求资源找不到"),
        @ApiResponse(code = 405,message = "请求方式不支持"),
        @ApiResponse(code = 403,message = "请求被拒绝"),
        @ApiResponse(code = 401,message = "没有权限访问"),

})
@Slf4j
public class CustomerContactController {
    @Autowired
 ICustomerContactService customerContactService;
    @GetMapping("page")
    @ApiOperation(value = "客户分页接口", notes = "客户分页接口")
    public DataResultsPage page(PageVo pageVo,Integer cusId,Integer linkManId) {
        QueryWrapper<CustomerContact> queryWrapper = new QueryWrapper<CustomerContact>();
        //条件查询
        queryWrapper.eq("isdel",0);
        queryWrapper.eq("cus_id",cusId);
        queryWrapper.eq("link_man_id",linkManId);
        queryWrapper.like((pageVo.getKey() != null && !"".equals(pageVo.getKey())), "outline", pageVo.getKey());

        //分页插件
        IPage<CustomerContact> page = customerContactService.page(new Page<CustomerContact>(pageVo.getPage(), pageVo.getLimit()), queryWrapper);
        log.info(page.toString());
        return DataResultsPage.success(ResultCode.PAGESUCCESS,page.getRecords(),"0",page.getTotal());
    }
    @PostMapping("save")
    @ApiOperation(value = "客户添加接口", notes = "客户添加接口")
    public DataResults save(@RequestBody CustomerContact customerContact) {
        customerContact.setCreateDate(DateTimeUtils.getDateTime());
        customerContact.setIsdel(0);
        log.info("接收的数据："+customerContact);
        return DataResults.success(ResultCode.SUCCESS,customerContactService.save(customerContact));
    }
    @PostMapping("update")
    @ApiOperation(value = "客户添加接口", notes = "客户添加接口")
    public DataResults update(@RequestBody CustomerContact customerContact) {
        customerContact.setCreateDate(DateTimeUtils.getDateTime());
        customerContact.setIsdel(0);
        log.info("接收的数据："+customerContact);
        return DataResults.success(ResultCode.SUCCESS,customerContactService.updateById(customerContact));
    }
    @DeleteMapping("delete")
    @ApiOperation(value = "客户添加接口", notes = "客户添加接口")
    public DataResults delete(Integer id) {
        CustomerContact customerContact = new CustomerContact();
        customerContact.setIsdel(1);
        customerContact.setId(id);
        log.info(customerContact.toString());
        log.info("接收的数据："+customerContact);
        return DataResults.success(ResultCode.SUCCESS,customerContactService.removeById(id));
    }

    @DeleteMapping("deleteMany")
    @ApiOperation(value = "客户添加接口", notes = "客户添加接口")
    public DataResults deleteMany(@RequestBody List<CustomerContact> list) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (CustomerContact customerContact:list) {
            ids.add(customerContact.getId());
        }
        return DataResults.success(ResultCode.SUCCESS,customerContactService.removeByIds(ids));

    }
}
