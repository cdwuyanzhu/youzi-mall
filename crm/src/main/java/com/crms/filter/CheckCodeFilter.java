package com.crms.filter;

import com.alibaba.druid.util.StringUtils;
import com.crms.utils.DataResults;
import com.crms.utils.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 自定义---校验验证码过滤器
 */
@Component
@Slf4j
public class CheckCodeFilter extends OncePerRequestFilter {

    /**
     * 校验验证码合法性
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        log.info("用户请求地址："+request.getRequestURI());
        //验证是登录请求，并且POST
        if (StringUtils.equals("/login", request.getRequestURI()) && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
            HttpSession session = request.getSession();
            //用户输入的验证码
            String checkcode = request.getParameter("validateNum");
            logger.info("用户输入的验证码:"+checkcode);
            //系统生成的验证码
            String checkCode_session = (String) session.getAttribute("checkCode_session");
            //不区分大小写比较
            if (!checkcode.equalsIgnoreCase(checkCode_session)) {
                //产生异常交给myFailureHandler处理
                DataResults dataResults = DataResults.success(ResultCode.CODE_FAIL); //9001
                // 人工转JSON
                String jsonResult = new ObjectMapper().writeValueAsString(dataResults);
                response.getWriter().write(jsonResult);
                return; //产生异常就不执行后面的过滤器链
            }
        }
        //执行后续过滤器
        filterChain.doFilter(request,response);
    }
}

