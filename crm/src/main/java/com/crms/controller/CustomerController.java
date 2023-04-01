package com.crms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.crms.annotation.CrmSystemLogging;
import com.crms.pojo.vo.Customer;
import com.crms.pojo.vo.CustomerContact;
import com.crms.service.ICustomerLinkManService;
import com.crms.service.ICustomerLossService;
import com.crms.service.ICustomerOrderService;
import com.crms.service.ICustomerService;
import com.crms.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bruce
 * @since 2022-12-24
 */
@RestController
@RequestMapping("/customer/")
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
public class CustomerController {
    @Autowired
    ICustomerService customerService;
    @Autowired
    ICustomerLinkManService customerLinkManService;
    @Autowired
    ICustomerOrderService customerOrderService;
    @Autowired
    ICustomerLossService customerLossService;
    @GetMapping("page")
    @ApiOperation(value = "客户分页接口", notes = "客户分页接口")
    @CrmSystemLogging(logDescription = "客户分页查询",methodType = "com.crm.utils.DataResultsPage")
    public DataResultsPage page(PageVo pageVo) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<Customer>();
        //条件查询
        queryWrapper.eq("isdel",0);
        queryWrapper.like((pageVo.getKey() != null && !"".equals(pageVo.getKey())), "name", pageVo.getKey());

        //分页插件
        IPage<Customer> page = customerService.page(new Page<Customer>(pageVo.getPage(), pageVo.getLimit()), queryWrapper);
        return DataResultsPage.success(ResultCode.PAGESUCCESS,page.getRecords(),"0",page.getTotal());
    }
    @PostMapping("save")
    @ApiOperation(value = "客户添加接口", notes = "客户添加接口")
    @CrmSystemLogging(logDescription = "新增客户",methodType = "com.crm.utils.DataResultsPage")
    public DataResults save(@RequestBody Customer customer) {
        customer.setCreateDate(DateTimeUtils.getDateTime());
        customer.setArea(customer.getProvince() +customer.getCity()+customer.getDistrict());
        customer.setIsdel(0);
        log.info("接收的数据："+customer);
        return DataResults.success(ResultCode.SUCCESS,customerService.save(customer));
    }
    @PostMapping("update")
    @ApiOperation(value = "客户更新接口", notes = "客户更新接口")
    public DataResults update(@RequestBody Customer customer) {
        customer.setUpdateDate(DateTimeUtils.getDateTime());
        customer.setArea(customer.getProvince() +customer.getCity()+customer.getDistrict());
        customer.setIsdel(0);
        log.info("接收的数据："+customer);
        return DataResults.success(ResultCode.SUCCESS,customerService.updateById(customer));
    }
    @DeleteMapping("delete")
    @ApiOperation(value = "客户添加接口", notes = "客户添加接口")
    public DataResults delete(Integer id) {
        Customer customer = new Customer();
        customer.setIsdel(1);
        customer.setId(id);
        log.info(customer.toString());
        log.info("接收的数据："+customer);
        return DataResults.success(ResultCode.SUCCESS,customerService.removeById(id));
    }

    @DeleteMapping("deleteMany")
    @ApiOperation(value = "删除接口", notes = "删除接口")
    public DataResults deleteMany(@RequestBody List<Customer> list) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Customer customer:list) {
            ids.add(customer.getId());
        }
        return DataResults.success(ResultCode.SUCCESS,customerService.removeByIds(ids));

    }
    @GetMapping("listChancedCustomers")
     public DataResults listChancedCustomers(){
        QueryWrapper<Customer> qw = new QueryWrapper<>();
        qw.eq("isdel",0);
        qw.eq("state",0);
        return DataResults.success(ResultCode.SUCCESS,customerService.list(qw));
     }
    @RequestMapping("findTotal")
    public Map<String,Integer> findTotal(HttpServletRequest request){
        //调用service的方法,拿到首页需要的所有数据
        //新增客户数
        int customerTotal = customerService.findSixTotal();
        //新增联系人数
        int linkManTotal = customerLinkManService.findSixTotal();
        //新增订单数
        int orderTotal = customerOrderService.findSixTotal();
        //流失客户数
        int customerLossTotal = customerLossService.findSixTotal();

        //1-6月新增客户数如下
        int one = customerService.findOneTotal();
        int two = customerService.findTwoTotal() - one;
        int three = customerService.findThreeTotal() - two - one;
        int four = customerService.findFourTotal() - three - two - one;
        int five = customerService.findFiveTotal() - four - three - two - one;
        int six = customerService.findSixTotal() - five - four - three - two - one;

        //放入map集合
        Map<String,Integer> map = new HashMap<>();
        map.put("customerTotal",customerTotal);
        map.put("linkManTotal",linkManTotal);
        map.put("orderTotal",orderTotal);
        map.put("customerLossTotal",customerLossTotal);

        map.put("one",one);
        map.put("two",two);
        map.put("three",three);
        map.put("four",four);
        map.put("five",five);
        map.put("six",six);
        return map;
    }
}
