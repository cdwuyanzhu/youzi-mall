package com.crms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crms.pojo.vo.CustomerLoss;
import com.crms.pojo.vo.CustomerOrder;
import com.crms.service.ICustomerOrderService;
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
@RequestMapping("/customer-order/")
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
public class CustomerOrderController {
    @Autowired
    ICustomerOrderService customerOrderService;
    @GetMapping("page")
    @ApiOperation(value = "客户分页接口", notes = "客户分页接口")
    public DataResultsPage page(PageVo pageVo) {
        QueryWrapper<CustomerOrder> queryWrapper = new QueryWrapper<CustomerOrder>();
        //条件查询
        queryWrapper.eq("isdel",0);
        queryWrapper.like((pageVo.getKey() != null && !"".equals(pageVo.getKey())), "order_date", pageVo.getKey());

        //分页插件
        IPage<CustomerOrder> page = customerOrderService.page(new Page<CustomerOrder>(pageVo.getPage(), pageVo.getLimit()), queryWrapper);
        log.info(page.toString());
        return DataResultsPage.success(ResultCode.PAGESUCCESS,page.getRecords(),"0",page.getTotal());
    }
    @PostMapping("save")
    @ApiOperation(value = "订单添加接口", notes = "订单添加接口")
    public DataResults save(@RequestBody CustomerOrder customerOrder) {
        customerOrder.setOrderDate(DateTimeUtils.getDateTime());
        customerOrder.setCreateDate(DateTimeUtils.getDateTime());
        customerOrder.setIsdel(0);
        log.info("接收的数据："+customerOrder);
        return DataResults.success(ResultCode.SUCCESS,customerOrderService.save(customerOrder));
    }
    @PostMapping("update")
    @ApiOperation(value = "订单更新接口", notes = "订单更新接口")
    public DataResults update(@RequestBody CustomerOrder customerOrder) {
        log.info("接收的数据："+customerOrder);
        customerOrder.setUpdateDate(DateTimeUtils.getDateTime());
        return DataResults.success(ResultCode.SUCCESS,customerOrderService.updateById(customerOrder));
    }
    @DeleteMapping("delete")
    @ApiOperation(value = "删除接口", notes = "删除接口")
    public DataResults delete(Integer id) {
        return DataResults.success(ResultCode.SUCCESS,customerOrderService.removeById(id));
    }

    @DeleteMapping("deleteMany")
    @ApiOperation(value = "多选删除接口", notes = "多选删除接口")
    public DataResults deleteMany(@RequestBody List<CustomerOrder> list) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (CustomerOrder customerOrder:list) {
            ids.add(customerOrder.getId());
        }
        return DataResults.success(ResultCode.SUCCESS,customerOrderService.removeByIds(ids));

    }
}
