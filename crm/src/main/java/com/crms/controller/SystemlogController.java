package com.crms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crms.pojo.vo.Systemlog;
import com.crms.service.ISystemlogService;
import com.crms.utils.DataResults;
import com.crms.utils.DataResultsPage;
import com.crms.utils.PageVo;
import com.crms.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bruce
 * @since 2023-01-08
 */
@RestController
@RequestMapping("/systemlog")
@Api(tags = "客户API", description = "提供客户信息相关的 Rest API")
@ApiResponses({
        @ApiResponse(code = 200, message = "请求成功"),
        @ApiResponse(code = 500, message = "服务器内部错误"),
        @ApiResponse(code = 404, message = "请求的资源找不到"),
        @ApiResponse(code = 405, message = "请求方式不支持"),
        @ApiResponse(code = 403, message = "请求被拒绝"),
        @ApiResponse(code = 401, message = "没有权限访问")
})
@Slf4j
public class SystemlogController {
    @Autowired
    ISystemlogService systemlogService;

    @GetMapping("page")
    @ApiOperation(value = "用户行为日志分页接口", notes = "用户行为日志分页接口")
    public DataResultsPage page(PageVo pageVo) {
        QueryWrapper<Systemlog> queryWrapper = new QueryWrapper<Systemlog>();
        //条件查询
        queryWrapper.like((pageVo.getKey() != null && !"".equals(pageVo.getKey())), "nowuser", pageVo.getKey());
        //分页插件
        IPage<Systemlog> page = systemlogService.page(new Page<Systemlog>(pageVo.getPage(), pageVo.getLimit()), queryWrapper);
        //数据封装
        return DataResultsPage.success(ResultCode.PAGESUCCESS,page.getRecords(),"0",page.getTotal());
    }

    @ApiOperation(value = "根据ID删除用户行为日志", notes = "根据ID删除用户行为日志")
    @DeleteMapping("delete")
    public DataResults delete(Integer id) {
        log.info("删除的ID:" + id);
        return DataResults.success(ResultCode.SUCCESS, systemlogService.removeById(id));
    }

    @ApiOperation(value = "批量删除用户行为日志", notes = "批量删除用户行为日志")
    @DeleteMapping("deleteMany")
    public DataResults deleteMany(@RequestBody List<Systemlog> systemlogList) {
        log.info("要删除的集合:" + systemlogList);
        List<Integer> ids=new ArrayList<Integer>();
        for (Systemlog systemlog : systemlogList) {
            ids.add(systemlog.getId());
        }
        //批量更新
        return DataResults.success(ResultCode.SUCCESS, systemlogService.removeByIds(ids));
    }
}
