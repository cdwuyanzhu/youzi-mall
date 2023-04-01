package com.crms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crms.pojo.vo.Permission;
import com.crms.pojo.vo.Role;
import com.crms.pojo.vo.User;
import com.crms.pojo.vo.UserRole;
import com.crms.service.IRolePermissionService;
import com.crms.service.IRoleService;
import com.crms.service.IUserRoleService;
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
 * @since 2022-12-30
 */
@RestController
@RequestMapping("/role/")
public class RoleController {
    @Autowired
    IRoleService roleService;
    @Autowired
    IUserRoleService userRoleService;
//    @GetMapping("listRoles")
//    @ApiOperation(value = "下拉列表", notes = "下拉列表")
//    public DataResults listRole() {
//        return DataResults.success(ResultCode.SUCCESS,  roleService.list(new QueryWrapper<Role>().eq("isdel",0).eq("is_valid",1)));
//    }
    @GetMapping("page")
    @ApiOperation(value = "user分页接口", notes = "user分页接口")
    public DataResultsPage page(PageVo pageVo) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
        //条件查询
        queryWrapper.eq("isdel", 0);
        queryWrapper.eq("is_valid",1);
        queryWrapper.like((pageVo.getKey() != null && !"".equals(pageVo.getKey())), "role_name", pageVo.getKey());

        //分页插件
        IPage<Role> page = roleService.page(new Page<Role>(pageVo.getPage(), pageVo.getLimit()), queryWrapper);
        return DataResultsPage.success(ResultCode.PAGESUCCESS, page.getRecords(), "0", page.getTotal());
    }
    @PostMapping("save")
    @ApiOperation(value = "role添加接口", notes = "role添加接口")
    public DataResults save(@RequestBody Role role) {
        role.setCreateDate(DateTimeUtils.getDateTime());
        role.setIsdel(0);
        return DataResults.success(ResultCode.SUCCESS, roleService.save(role));
    }

    @PostMapping("update")
    @ApiOperation(value = "role更新接口", notes = "role更新接口")
    public DataResults update(@RequestBody Role role) {
        role.setUpdateDate(DateTimeUtils.getDateTime());
        role.setIsdel(0);
        return DataResults.success(ResultCode.SUCCESS, roleService.updateById(role));
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "role多选删除接口", notes = "role多选删除接口")
    public DataResults delete(Integer id) {
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("isdel",0);
        userRoleQueryWrapper.eq("user_id",id);
        int count = userRoleService.count(userRoleQueryWrapper);
        if (count > 0) {
            return DataResults.success(ResultCode.NO_DELETE);
        }else {
            Role role = new Role();
            role.setIsdel(1);
            role.setId(id);
            return DataResults.success(ResultCode.SUCCESS, roleService.removeById(role));
        }

    }

    @DeleteMapping("deleteMany")
    @ApiOperation(value = "role多选删除接口", notes = "role多选删除接口")
    public DataResults deleteMany(@RequestBody List<Role> list) {
        ArrayList<String> strName = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        for (Role role : list) {

            int count = userRoleService.count(new QueryWrapper<UserRole>().eq("isdel", 0).eq("role_id", role.getId()));
            if (count >0) {
                strName.add(role.getRoleName());
            }
            role.setIsdel(1);
            ids.add(role.getId());
        }
        if (strName.size()>0) {
            return DataResults.success(ResultCode.NO_DELETE,strName);
        }else {
            return DataResults.success(ResultCode.SUCCESS, roleService.removeByIds(ids));
        }

    }


    @GetMapping("listRoles")
    @ApiOperation(value = "Permission下拉框绑定接口", notes = "Permission下拉框绑定接口")
    public DataResults listRoles(){
        QueryWrapper<Role> qw = new QueryWrapper<Role>();
        qw.eq("isdel",0);
        qw.eq("is_valid",1);
        return DataResults.success(ResultCode.SUCCESS,roleService.list(qw));
    }
}
