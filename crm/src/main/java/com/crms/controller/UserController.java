package com.crms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crms.pojo.vo.Customer;
import com.crms.pojo.vo.User;
import com.crms.service.IUserService;
import com.crms.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/user/")
@Api(tags = "userAPI",description = "userAPI")
@ApiResponses({
        @ApiResponse(code = 200,message = "请求成功"),
        @ApiResponse(code = 500,message = "服务器内部错误"),
        @ApiResponse(code = 404,message = "请求资源找不到"),
        @ApiResponse(code = 405,message = "请求方式不支持"),
        @ApiResponse(code = 403,message = "请求被拒绝"),
        @ApiResponse(code = 401,message = "没有权限访问"),

})
@Slf4j
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;
    @GetMapping("getUserList/{roleId}")
    public DataResults getUserList(@PathVariable("roleId") Integer roleId) {
        return DataResults.success(ResultCode.SUCCESS, userService.getUserList(roleId));
    }

    @GetMapping("page")
    @ApiOperation(value = "user分页接口", notes = "user分页接口")
    public DataResultsPage page(PageVo pageVo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        //条件查询
        queryWrapper.eq("isdel", 0);
        queryWrapper.like((pageVo.getKey() != null && !"".equals(pageVo.getKey())), "user_name", pageVo.getKey());

        //分页插件
        IPage<User> page = userService.page(new Page<User>(pageVo.getPage(), pageVo.getLimit()), queryWrapper);
        return DataResultsPage.success(ResultCode.PAGESUCCESS, page.getRecords(), "0", page.getTotal());
    }

    @PostMapping("save")
    @ApiOperation(value = "user添加接口", notes = "user添加接口")
    public DataResults save(@RequestBody User user) {
        user.setCreateDate(DateTimeUtils.getDateTime());
        String encode = bCryptPasswordEncoder.encode(user.getUserPwd());
        user.setUserPwd(encode);
        user.setIsdel(0);
        log.info("接收的数据：" + user);
        return DataResults.success(ResultCode.SUCCESS, userService.save(user));
    }

    @PostMapping("update")
    @ApiOperation(value = "user更新接口", notes = "user更新接口")
    public DataResults update(@RequestBody User user) {
        user.setUpdateDate(DateTimeUtils.getDateTime());
        user.setIsdel(0);
        user.setUpdateDate(DateTimeUtils.getDateTime());
        log.info("接收的数据：" + user);
        return DataResults.success(ResultCode.SUCCESS, userService.updateById(user));
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "user删除接口", notes = "user删除接口")
    public DataResults delete(Integer id) {
        User user = new User();
        user.setIsdel(1);
        user.setId(id);
        return DataResults.success(ResultCode.SUCCESS, userService.removeById(id));
    }

    @DeleteMapping("deleteMany")
    @ApiOperation(value = "user多选删除接口", notes = "user多选删除接口")
    public DataResults deleteMany(@RequestBody List<User> list) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (User user : list) {
            ids.add(user.getId());
        }
        return DataResults.success(ResultCode.SUCCESS, userService.removeByIds(ids));


    }

    @GetMapping("listUsers")
    @ApiOperation(value = "下拉列表", notes = "下拉列表")
    public DataResults listUsers() {
        return DataResults.success(ResultCode.SUCCESS,  userService.list(new QueryWrapper<User>().eq("isdel",0).eq("is_valid",1)));
    }
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @PostMapping("updateuser")
    public DataResults updateuser(@RequestBody User user, HttpSession session){
        session.invalidate();
        user.setUpdateDate(DateTimeUtils.getDateTime());//更新时间，当前系统时间
        return DataResults.success(ResultCode.SUCCESS,userService.updateById(user));
    }
    @ApiOperation(value = "检查原密码", notes = "检查原密码")
    @PostMapping("checkPwd")
    public DataResults checkPwd(String userPwd,HttpSession session){
        log.info("原密码="+userPwd);
        User user= (User) session.getAttribute("user");
        boolean matches = bCryptPasswordEncoder.matches(userPwd, user.getUserPwd());
        if(matches){
            return DataResults.success(ResultCode.SUCCESS);
        }else{
            return DataResults.success(ResultCode.PASSWORD_ERROR);
        }
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PostMapping("updatePwd")
    public DataResults updatePwd(String userPwd1,String userPwd2,HttpSession session){
        User user= (User) session.getAttribute("user");
        if(userPwd1.equals(userPwd2)){
            String encode = bCryptPasswordEncoder.encode(userPwd1);
            user.setUserPwd(encode);
            userService.updateById(user);
            session.invalidate();
            return DataResults.success(ResultCode.SUCCESS);
        }else{
            return DataResults.success(ResultCode.REPASSWORD_ERROR);
        }
    }
}
