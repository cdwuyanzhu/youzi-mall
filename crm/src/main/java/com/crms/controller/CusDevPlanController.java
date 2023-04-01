package com.crms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crms.pojo.vo.CusDevPlan;
import com.crms.pojo.vo.Customer;
import com.crms.service.ICusDevPlanService;
import com.crms.utils.*;
import io.swagger.annotations.ApiOperation;
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
 * @since 2022-12-29
 */
@RestController
@RequestMapping("/cus-dev-plan")
public class CusDevPlanController {
    @Autowired
    ICusDevPlanService cusDevPlanService;
    @GetMapping("page")
    @ApiOperation(value = "客户分页接口", notes = "客户分页接口")

    public DataResultsPage page(PageVo pageVo) {
        QueryWrapper<CusDevPlan> queryWrapper = new QueryWrapper<CusDevPlan>();
        //条件查询
        queryWrapper.eq("isdel",0);
        queryWrapper.like((pageVo.getKey() != null && !"".equals(pageVo.getKey())), "plan_content", pageVo.getKey());

        //分页插件
        IPage<CusDevPlan> page = cusDevPlanService.page(new Page<CusDevPlan>(pageVo.getPage(), pageVo.getLimit()), queryWrapper);
        return DataResultsPage.success(ResultCode.PAGESUCCESS,page.getRecords(),"0",page.getTotal());
    }


    @PostMapping("save")
    @ApiOperation(value = "客户开发添加接口", notes = "客户开发添加接口")
    public DataResults save(@RequestBody CusDevPlan cusDevPlan) {
        cusDevPlan.setCreateDate(DateTimeUtils.getDateTime());
        return DataResults.success(ResultCode.SUCCESS,cusDevPlanService.save(cusDevPlan));
    }
    @PostMapping("update")
    @ApiOperation(value = "客户开发计划更新接口", notes = "客户开发计划更新接口")
    public DataResults update(@RequestBody CusDevPlan cusDevPlan) {
        cusDevPlan.setUpdateDate(DateTimeUtils.getDateTime());
        return DataResults.success(ResultCode.SUCCESS,cusDevPlanService.updateById(cusDevPlan));
    }
    @DeleteMapping("delete")
    @ApiOperation(value = "客户开发计划删除接口", notes = "客户开发计划删除接口")
    public DataResults delete(Integer id) {
        CusDevPlan cusDevPlan = new CusDevPlan();
        cusDevPlan.setIsdel(1);
        cusDevPlan.setId(id);
        return DataResults.success(ResultCode.SUCCESS,cusDevPlanService.removeById(cusDevPlan));
    }

    @DeleteMapping("deleteMany")
    @ApiOperation(value = "客户开发计划多选删除接口", notes = "客户开发计划多选删除接口")
    public DataResults deleteMany(@RequestBody List<CusDevPlan> list) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (CusDevPlan cusDevPlan:list) {
            ids.add(cusDevPlan.getId());
        }
        return DataResults.success(ResultCode.SUCCESS,cusDevPlanService.removeByIds(ids));

    }
}
