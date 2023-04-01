package com.crms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crms.pojo.vo.Customer;
import com.crms.pojo.vo.CustomerLinkMan;
import com.crms.service.ICustomerLinkManService;
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
@RequestMapping("/customer-link-man/")
@RestController
@Api(tags = "客户API",description = "查看联系人信息相关的Rest API")
@ApiResponses({
        @ApiResponse(code = 200,message = "请求成功"),
        @ApiResponse(code = 500,message = "服务器内部错误"),
        @ApiResponse(code = 404,message = "请求资源找不到"),
        @ApiResponse(code = 405,message = "请求方式不支持"),
        @ApiResponse(code = 403,message = "请求被拒绝"),
        @ApiResponse(code = 401,message = "没有权限访问"),

})
@Slf4j
public class CustomerLinkManController {
    @Autowired
  ICustomerLinkManService customerLinkManService;
    @GetMapping("page")
    @ApiOperation(value = "客户分页接口", notes = "客户分页接口")
    public DataResultsPage page(PageVo pageVo,Integer cusId) {
//        System.out.println(cusId);
//        System.out.println(pageVo);
        log.info(pageVo.toString());
        log.info(cusId.toString());
        QueryWrapper<CustomerLinkMan> queryWrapper = new QueryWrapper<CustomerLinkMan>();
        //条件查询
        queryWrapper.eq("isdel",0);
        queryWrapper.eq("cus_id",cusId);
        queryWrapper.like((pageVo.getKey() != null && !"".equals(pageVo.getKey())), "link_man", pageVo.getKey());

        //分页插件
        IPage<CustomerLinkMan> page = customerLinkManService.page(new Page<CustomerLinkMan>(pageVo.getPage(), pageVo.getLimit()), queryWrapper);
        return DataResultsPage.success(ResultCode.PAGESUCCESS,page.getRecords(),"0",page.getTotal());
    }
    @PostMapping("save")
    @ApiOperation(value = "客户添加接口", notes = "客户添加接口")
    public DataResults save(@RequestBody CustomerLinkMan customerLinkMan) {
        customerLinkMan.setCreateDate(DateTimeUtils.getDateTime());
        customerLinkMan.setIsdel(0);
        log.info("接收的数据："+customerLinkMan);
        return DataResults.success(ResultCode.SUCCESS,customerLinkManService.save(customerLinkMan));
    }
    @PostMapping("update")
    @ApiOperation(value = "联系人更新接口", notes = "联系人更新接口")
    public DataResults update(@RequestBody CustomerLinkMan customerLinkMan) {
        customerLinkMan.setUpdateDate(DateTimeUtils.getDateTime());
        customerLinkMan.setIsdel(0);
        log.info("接收的数据："+customerLinkMan);
        return DataResults.success(ResultCode.SUCCESS,customerLinkManService.updateById(customerLinkMan));
    }
    @DeleteMapping("delete")
    @ApiOperation(value = "删除接口", notes = "删除接口")
    public DataResults delete(Integer id) {
        CustomerLinkMan customerLinkMan = new CustomerLinkMan();
        customerLinkMan.setIsdel(1);
        customerLinkMan.setId(id);
        log.info(customerLinkMan.toString());
        log.info("接收的数据："+customerLinkMan);
        return DataResults.success(ResultCode.SUCCESS,customerLinkManService.removeById(id));
    }

    @DeleteMapping("deleteMany")
    @ApiOperation(value = "list删除接口", notes = "list删除接口")
    public DataResults deleteMany(@RequestBody List<CustomerLinkMan> list) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (CustomerLinkMan customerLinkMan:list) {
            ids.add(customerLinkMan.getId());
        }
        return DataResults.success(ResultCode.SUCCESS,customerLinkManService.removeByIds(ids));

    }
}
