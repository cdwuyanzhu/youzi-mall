package com.crms.handler;
import com.crms.utils.DataResults;
import com.crms.utils.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler  extends SimpleUrlAuthenticationFailureHandler {

    /**
     * 认证失败消息处理器
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        logger.info("登录失败:"+exception.getMessage()+",异常类型是:"+exception); // User is disabled
        String msg="账号或密码错误！";
        if(exception instanceof DisabledException){
            msg="该账户不可用，联系管理员！";
        }
        //浏览器响应消息
        DataResults dataResults=DataResults.success(ResultCode.LOGIN_FAIL,msg);
        response.getWriter().write(new ObjectMapper().writeValueAsString(dataResults));
        return;
    }
}
