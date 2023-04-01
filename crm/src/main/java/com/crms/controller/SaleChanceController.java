package com.crms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crms.pojo.vo.*;
import com.crms.service.ICustomerService;
import com.crms.service.ISaleChanceService;
import com.crms.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bruce
 * @since 2022-12-28
 */
@RestController
@RequestMapping("/sale-chance/")
@Api(tags = "营销机会API",description = "营销机会API")
@ApiResponses({
        @ApiResponse(code = 200,message = "请求成功"),
        @ApiResponse(code = 500,message = "服务器内部错误"),
        @ApiResponse(code = 404,message = "请求资源找不到"),
        @ApiResponse(code = 405,message = "请求方式不支持"),
        @ApiResponse(code = 403,message = "请求被拒绝"),
        @ApiResponse(code = 401,message = "没有权限访问"),

})
@Slf4j
public class SaleChanceController {
    @Autowired
     ISaleChanceService saleChanceService;
    @Autowired
    ICustomerService customerService;
    @GetMapping("page")
    @ApiOperation(value = "营销机会分页接口", notes = "营销机会分页接口")
    public DataResultsPage page(PageVo pageVo) {
        QueryWrapper<SaleChance> queryWrapper = new QueryWrapper<SaleChance>();
        //条件查询
        queryWrapper.eq("isdel",0);
        queryWrapper.like((pageVo.getKey() != null && !"".equals(pageVo.getKey())), "customer_name", pageVo.getKey());

        //分页插件
        IPage<SaleChance> page = saleChanceService.page(new Page<SaleChance>(pageVo.getPage(), pageVo.getLimit()), queryWrapper);
        log.info(page.toString());
        return DataResultsPage.success(ResultCode.PAGESUCCESS,page.getRecords(),"0",page.getTotal());
    }
    @PostMapping("save")
    @ApiOperation(value = "营销机会添加接口", notes = "营销机会添加接口")
    public DataResults save(@RequestBody SaleChance saleChance, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Customer customer = customerService.getById(saleChance.getCustomerId());
        saleChance.setCustomerName(customer.getName());
        saleChance.setCreateMan(String.valueOf(user.getId()));
        saleChance.setAssignTime(DateTimeUtils.getDateTime());
        log.info("接收的数据："+saleChance);
        return DataResults.success(ResultCode.SUCCESS,saleChanceService.save(saleChance));
    }
    @PostMapping("update")
    @ApiOperation(value = "营销机会更新接口", notes = "营销机会更新接口")
    public DataResults update(@RequestBody SaleChance saleChance,HttpSession session) {
        User user = (User) session.getAttribute("user");
        saleChance.setCreateMan(String.valueOf(user.getId()));
        Customer customer = customerService.getById(saleChance.getCustomerId());
        saleChance.setCustomerName(customer.getName());
        saleChance.setUpdateDate(DateTimeUtils.getDateTime());
        log.info("接收的数据："+saleChance);
        return DataResults.success(ResultCode.SUCCESS,saleChanceService.updateById(saleChance));
    }
    @DeleteMapping("delete")
    @ApiOperation(value = "营销机会删除接口", notes = "营销机会删除接口")
    public DataResults delete(Integer id) {
        return DataResults.success(ResultCode.SUCCESS,saleChanceService.removeById(id));
    }

    @DeleteMapping("deleteMany")
    @ApiOperation(value = "营销机会多选删除接口", notes = "营销机会多选删除接口")
    public DataResults deleteMany(@RequestBody List<SaleChance> list) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (SaleChance saleChance:list) {
            ids.add(saleChance.getId());
        }
        return DataResults.success(ResultCode.SUCCESS,saleChanceService.removeByIds(ids));

    }

    @GetMapping("listSaleChances")
    @ApiOperation(value = "客户开发下拉框绑定接口", notes = "客户开发下拉框绑定接口")
    public DataResults listSaleChances(){
        QueryWrapper<SaleChance> qw = new QueryWrapper<SaleChance>();
        qw.eq("isdel",0);
        qw.eq("is_valid",1);
        return DataResults.success(ResultCode.SUCCESS,saleChanceService.list(qw));
    }
}
