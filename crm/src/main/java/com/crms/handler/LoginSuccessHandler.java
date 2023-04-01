package com.crms.handler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crms.pojo.vo.Permission;
import com.crms.pojo.vo.Role;
import com.crms.pojo.vo.User;
import com.crms.service.IPermissionService;
import com.crms.service.IRoleService;
import com.crms.service.IUserService;
import com.crms.utils.DataResults;
import com.crms.utils.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 登录成功的处理器
 */
@Component
@Slf4j
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    IUserService userService;
    @Autowired
    IRoleService roleService;
    @Autowired
    IPermissionService permissionService;
    /**
     * 认证成功消息处理器
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        //封装登录成功的账户和密码
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        HttpSession session = request.getSession();
        User user = userService.getOne(new QueryWrapper<User>().eq("user_name", userDetails.getUsername()).eq("isdel",0));
        session.setAttribute("user",user);
        List<Role> roles = roleService.findRoleByUserName(user.getUserName());
        session.setAttribute("roles",roles);
        List<Permission> permissions = permissionService.findPermissionList(user.getUserName());
        session.setAttribute("permissions",permissions);
        logger.info("登录成功:"+user);
        logger.info("user权限----》"+permissions);
        //浏览器响应消息 200
        DataResults dataResults=DataResults.success(ResultCode.SUCCESS);
        // 向浏览器响应一段JSON数据
        response.getWriter().write(new ObjectMapper().writeValueAsString(dataResults));
        return;
    }

}
