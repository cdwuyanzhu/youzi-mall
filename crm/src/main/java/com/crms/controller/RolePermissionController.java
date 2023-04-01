package com.crms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crms.pojo.vo.*;
import com.crms.service.IPermissionService;
import com.crms.service.IRolePermissionService;
import com.crms.service.IRoleService;
import com.crms.utils.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * @since 2022-12-30
 */
@RestController
@RequestMapping("/role-permission/")
@Slf4j
public class RolePermissionController {
    @Autowired
    IRoleService roleService;
    @Autowired
    IRolePermissionService rolePermissionService;
    @Autowired
    IPermissionService permissionService;
    @GetMapping("page")
    @ApiOperation(value = "RolePermission分页接口", notes = "RolePermission分页接口")
    public DataResultsPage page(PageVo pageVo) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
        //条件查询
        queryWrapper.eq("isdel", 0);
        queryWrapper.eq("is_valid",1);
        queryWrapper.like((pageVo.getKey() != null && !"".equals(pageVo.getKey())), "role_name", pageVo.getKey());
        List<Role> list = roleService.list(queryWrapper);
        ArrayList<Integer> roleIdList = new ArrayList<>();
        for (Role role:list) {
           roleIdList.add(role.getId());
        }
        QueryWrapper<RolePermission> queryWrapper1 = new QueryWrapper<RolePermission>();
        //条件查询
        queryWrapper1.eq("isdel", 0);
        queryWrapper1.in(roleIdList.size()>0,"role_id",roleIdList);
        //分页插件
        IPage<RolePermission> page = rolePermissionService.page(new Page<RolePermission>(pageVo.getPage(), pageVo.getLimit()), queryWrapper1);
        List<RolePermission> records = page.getRecords();
        for (RolePermission rolePermission:records) {
            Role role = roleService.getById(rolePermission.getRoleId());
            rolePermission.setRoleName(role.getRoleName());
            Permission permission = permissionService.getById(rolePermission.getPermissionId());
            rolePermission.setPermissionName(permission.getPermissionName());
        }
        return DataResultsPage.success(ResultCode.PAGESUCCESS,records, "0", page.getTotal());
    }
    @PostMapping("save")
    @ApiOperation(value = "RolePermission添加接口", notes = "RolePermission添加接口")
    public DataResults save(@RequestBody RolePermission rolePermission,HttpSession session) {
        rolePermission.setCreateDate(DateTimeUtils.getDateTime());
        rolePermission.setIsdel(0);
        rolePermissionService.save(rolePermission);
        //refreshPermmsions(session);
        //刷新当前登录用户具备的权限
        User loginUser= (User) session.getAttribute("user");
        //当前登录用户新的权限集合
        List<Permission> permissions = permissionService.queryPermissionByType(loginUser.getUserName());
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        for (Permission p : permissions) {
            authorities.add(new SimpleGrantedAuthority(p.getPermissionName()));
            log.info(loginUser.getUserName()+"-->新的具备的权限有:"+p.getPermissionName());
        }
        // 生成新的认证信息
        Authentication newAuth = new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getUserPwd(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return DataResults.success(ResultCode.SUCCESS);
    }
    private void refreshPermmsions(HttpSession session) {
        //刷新当前登录用户具备的权限
        User loginUser= (User) session.getAttribute("user");
        //当前登录用户新的权限集合
        List<Permission> permissions = permissionService.queryPermissionByType(loginUser.getUserName());
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        for (Permission p : permissions) {
            authorities.add(new SimpleGrantedAuthority(p.getPermissionName()));
            log.info(loginUser.getUserName()+"-->新的具备的权限有:"+p.getPermissionName());
        }
        // 生成新的认证信息
        Authentication newAuth = new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getUserPwd(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
    @PostMapping("update")
    @ApiOperation(value = "RolePermission更新接口", notes = "RolePermission更新接口")
    public DataResults update(@RequestBody RolePermission rolePermission) {
        rolePermission.setUpdateDate(DateTimeUtils.getDateTime());
        rolePermission.setIsdel(0);
        return DataResults.success(ResultCode.SUCCESS, rolePermissionService.updateById(rolePermission));
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "RolePermission删除接口", notes = "RolePermission删除接口")
    public DataResults delete(Integer id) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setIsdel(1);
        rolePermission.setId(id);
        return DataResults.success(ResultCode.SUCCESS, rolePermissionService.removeById(rolePermission));
    }

    @DeleteMapping("deleteMany")
    @ApiOperation(value = "RolePermission多选删除接口", notes = "RolePermission多选删除接口")
    public DataResults deleteMany(@RequestBody List<RolePermission> list) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (RolePermission rolePermission : list) {
            ids.add(rolePermission.getId());
        }
        return DataResults.success(ResultCode.SUCCESS, rolePermissionService.removeByIds(ids));


    }
}
