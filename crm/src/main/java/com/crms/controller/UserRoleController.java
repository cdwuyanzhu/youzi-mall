package com.crms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crms.pojo.vo.Role;
import com.crms.pojo.vo.User;
import com.crms.pojo.vo.UserRole;
import com.crms.service.IRoleService;
import com.crms.service.IUserRoleService;
import com.crms.service.IUserService;
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
@RequestMapping("/user-role/")
@Api(tags = "userRoleAPI",description = "userRoleAPI")
@ApiResponses({
        @ApiResponse(code = 200,message = "请求成功"),
        @ApiResponse(code = 500,message = "服务器内部错误"),
        @ApiResponse(code = 404,message = "请求资源找不到"),
        @ApiResponse(code = 405,message = "请求方式不支持"),
        @ApiResponse(code = 403,message = "请求被拒绝"),
        @ApiResponse(code = 401,message = "没有权限访问"),

})
@Slf4j
public class UserRoleController {
    @Autowired
    IUserRoleService userRoleService;
    @Autowired
    IUserService userService;
    @Autowired
    IRoleService roleService;
    @GetMapping("page")
    @ApiOperation(value = "user分页接口", notes = "user分页接口")
    public DataResultsPage page(PageVo pageVo) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        //条件查询
        userQueryWrapper.eq("isdel", 0);
        userQueryWrapper.like((pageVo.getKey() != null && !"".equals(pageVo.getKey())), "user_name", pageVo.getKey());
        List<User> userList = userService.list(userQueryWrapper);
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (User user:userList) {
            arrayList.add(user.getId());
        }

        //分页插件
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<UserRole>();
        //条件查询
        queryWrapper.eq("isdel", 0);
         queryWrapper.in(arrayList.size()>0,"user_id",arrayList);
        //分页插件
        IPage<UserRole> page = userRoleService.page(new Page<UserRole>(pageVo.getPage(), pageVo.getLimit()), queryWrapper);
        List<UserRole> records = page.getRecords();
        for (UserRole record:records) {
            User user = userService.getById(record.getUserId());
            record.setUserName(user.getUserName());
            Role role = roleService.getById(record.getRoleId());
            record.setRoleName(role.getRoleName());
        }

        return DataResultsPage.success(ResultCode.PAGESUCCESS,records ,"0", page.getTotal());
    }
    @PostMapping("save")
    @ApiOperation(value = "user添加接口", notes = "user添加接口")
    public DataResults save(@RequestBody UserRole userRole) {
        userRole.setCreateDate(DateTimeUtils.getDateTime());
        userRole.setIsdel(0);
        log.info("接收的数据：" + userRole);
        return DataResults.success(ResultCode.SUCCESS, userRoleService.save(userRole));
    }

    @PostMapping("update")
    @ApiOperation(value = "user更新接口", notes = "user更新接口")
    public DataResults update(@RequestBody UserRole userRole) {
        userRole.setUpdateDate(DateTimeUtils.getDateTime());
        userRole.setIsdel(0);
        log.info("接收的数据：" + userRole);
        return DataResults.success(ResultCode.SUCCESS, userRoleService.updateById(userRole));
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "user删除接口", notes = "user删除接口")
    public DataResults delete(Integer id) {
        UserRole userRole = new UserRole();
        userRole.setIsdel(1);
        userRole.setId(id);
        return DataResults.success(ResultCode.SUCCESS, userRoleService.removeById(id));
    }

    @DeleteMapping("deleteMany")
    @ApiOperation(value = "user多选删除接口", notes = "user多选删除接口")
    public DataResults deleteMany(@RequestBody List<UserRole> list) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (UserRole userRole : list) {
            ids.add(userRole.getId());
        }
        return DataResults.success(ResultCode.SUCCESS, userRoleService.removeByIds(ids));


    }
}
