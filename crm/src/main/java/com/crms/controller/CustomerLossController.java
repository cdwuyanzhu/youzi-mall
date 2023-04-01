package com.crms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crms.pojo.vo.Customer;
import com.crms.pojo.vo.CustomerContact;
import com.crms.pojo.vo.CustomerLinkMan;
import com.crms.pojo.vo.CustomerLoss;
import com.crms.service.ICustomerLossService;
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
 * @since 2022-12-27
 */
@RequestMapping("/customer-loss/")
@RestController
@Api(tags = "流失客户API",description = "流失客户API")
@ApiResponses({
        @ApiResponse(code = 200,message = "请求成功"),
        @ApiResponse(code = 500,message = "服务器内部错误"),
        @ApiResponse(code = 404,message = "请求资源找不到"),
        @ApiResponse(code = 405,message = "请求方式不支持"),
        @ApiResponse(code = 403,message = "请求被拒绝"),
        @ApiResponse(code = 401,message = "没有权限访问"),

})
@Slf4j
public class CustomerLossController {
    @Autowired
    ICustomerLossService customerLossService;
    @GetMapping("page")
    @ApiOperation(value = "客户分页接口", notes = "客户分页接口")
    public DataResultsPage page(PageVo pageVo) {
        QueryWrapper<CustomerLoss> queryWrapper = new QueryWrapper<CustomerLoss>();
        //条件查询
        queryWrapper.eq("isdel",0);
        queryWrapper.like((pageVo.getKey() != null && !"".equals(pageVo.getKey())), "outline", pageVo.getKey());

        //分页插件
        IPage<CustomerLoss> page = customerLossService.page(new Page<CustomerLoss>(pageVo.getPage(), pageVo.getLimit()), queryWrapper);
        log.info(page.toString());
        return DataResultsPage.success(ResultCode.PAGESUCCESS,page.getRecords(),"0",page.getTotal());
    }
    @PostMapping("save")
    @ApiOperation(value = "流失客户添加接口", notes = "流失客户添加接口")
    public DataResults save(@RequestBody CustomerLoss customerLoss) {
        customerLoss.setConfirmLossTime(DateTimeUtils.getDateTime());
        customerLoss.setCreateTime(DateTimeUtils.getDateTime());
        customerLoss.setIsdel(0);
        log.info("接收的数据："+customerLoss);
        customerLossService.customerLoss(customerLoss);
        return DataResults.success(ResultCode.SUCCESS);
    }
    @PostMapping("update")
    @ApiOperation(value = "联系人更新接口", notes = "联系人更新接口")
    public DataResults update(@RequestBody CustomerLoss customerLoss) {
        customerLoss.setConfirmLossTime(DateTimeUtils.getDateTime());
        log.info("接收的数据："+customerLoss);
        return DataResults.success(ResultCode.SUCCESS,customerLossService.updateById(customerLoss));
    }
    @DeleteMapping("delete")
    @ApiOperation(value = "客户添加接口", notes = "客户添加接口")
    public DataResults delete(Integer id) {
//        CustomerLoss customerLoss = new CustomerLoss();
//        customerLoss.setIsdel(1);
//        customerLoss.setId(id);
//        log.info(customerLoss.toString());
        return DataResults.success(ResultCode.SUCCESS,customerLossService.removeById(id));
    }

    @DeleteMapping("deleteMany")
    @ApiOperation(value = "删除接口", notes = "删除接口")
    public DataResults deleteMany(@RequestBody List<CustomerLoss> list) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (CustomerLoss customerLoss:list) {
            ids.add(customerLoss.getId());
        }
        return DataResults.success(ResultCode.SUCCESS,customerLossService.removeByIds(ids));

    }
}
