package com.crms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crms.pojo.vo.*;
import com.crms.service.IPermissionService;
import com.crms.service.IRolePermissionService;
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
 * @since 2022-12-30
 */
@RestController
@RequestMapping("/permission/")
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
public class PermissionController {
    @Autowired
    IPermissionService permissionService;
    @Autowired
    IRolePermissionService rolePermissionService;
    @GetMapping("page")
    @ApiOperation(value = "Permission分页接口", notes = "Permission分页接口")
    public DataResultsPage page(PageVo pageVo) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        //条件查询
        queryWrapper.eq("isdel", 0);
        queryWrapper.eq("is_valid",1);
        queryWrapper.like((pageVo.getKey() != null && !"".equals(pageVo.getKey())), "permission_name", pageVo.getKey());

        //分页插件
        IPage<Permission> page = permissionService.page(new Page<Permission>(pageVo.getPage(), pageVo.getLimit()), queryWrapper);
        return DataResultsPage.success(ResultCode.PAGESUCCESS, page.getRecords(), "0", page.getTotal());
    }

    @PostMapping("save")
    @ApiOperation(value = "permission添加接口", notes = "permission添加接口")
    public DataResults save(@RequestBody Permission permission) {
        permission.setCreateDate(DateTimeUtils.getDateTime());
        permission.setIsdel(0);
        return DataResults.success(ResultCode.SUCCESS, permissionService.save(permission));
    }

    @PostMapping("update")
    @ApiOperation(value = "permissio更新接口", notes = "permissio更新接口")
    public DataResults update(@RequestBody Permission permission) {
        permission.setUpdateDate(DateTimeUtils.getDateTime());
        permission.setIsdel(0);
        return DataResults.success(ResultCode.SUCCESS, permissionService.updateById(permission));
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "permissio多选删除接口", notes = "permissio多选删除接口")
    public DataResults delete(Integer id) {
        QueryWrapper<RolePermission> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("isdel",0);
        userRoleQueryWrapper.eq("permission_id",id);
        int count = rolePermissionService.count(userRoleQueryWrapper);
        if (count > 0) {
            return DataResults.success(ResultCode.NO_DELETE);
        }else {
            Role role = new Role();
            role.setIsdel(1);
            role.setId(id);
            return DataResults.success(ResultCode.SUCCESS, permissionService.removeById(role));
        }

    }

    @DeleteMapping("deleteMany")
    @ApiOperation(value = "permissio多选删除接口", notes = "permissio多选删除接口")
    public DataResults deleteMany(@RequestBody List<Permission> list) {
        ArrayList<String> strName = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        for (Permission permission : list) {

            int count = rolePermissionService.count(new QueryWrapper<RolePermission>().eq("isdel", 0).eq("permission_id", permission.getId()));
            if (count >0) {
                strName.add(permission.getPermissionName());
            }
            permission.setIsdel(1);
            ids.add(permission.getId());
        }
        if (strName.size()>0) {
            return DataResults.success(ResultCode.NO_DELETE,strName);
        }else {
            return DataResults.success(ResultCode.SUCCESS, permissionService.removeByIds(ids));
        }

    }


    @GetMapping("/listPermissionByParentId/{id}")
    @ApiOperation(value = "Permission下拉框绑定接口", notes = "Permission下拉框绑定接口")
    public DataResults listPermissionByParentId(@PathVariable("id") Integer parentId){
        QueryWrapper<Permission> qw = new QueryWrapper<Permission>();
        qw.eq("isdel",0);
        qw.eq("is_valid",1);
        qw.eq("parent_id",parentId);
        return DataResults.success(ResultCode.SUCCESS,permissionService.list(qw));
    }

    @GetMapping("listPermissions")
    @ApiOperation(value = "Permission下拉框绑定接口", notes = "Permission下拉框绑定接口")
    public DataResults listPermissions(){
        QueryWrapper<Permission> qw = new QueryWrapper<Permission>();
        qw.eq("isdel",0);
        qw.eq("is_valid",1);
        return DataResults.success(ResultCode.SUCCESS,permissionService.list(qw));
    }
}
