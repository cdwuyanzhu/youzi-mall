package com.crms.aop;


import com.crms.annotation.CrmSystemLogging;
import com.crms.pojo.vo.Systemlog;
import com.crms.pojo.vo.User;
import com.crms.service.ISystemlogService;
import com.crms.utils.DateTimeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.DateUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 切面类 增强类
 *
 *          <dependency>
 *             <groupId>org.springframework</groupId>
 *             <artifactId>spring-aspects</artifactId>
 *         </dependency>
 */
@Aspect
@Component
public class CrmLoggerAdvice {

    @Autowired
    HttpSession session;

    @Autowired
    ISystemlogService systemLogService;

    /**
     * 环绕增强
     * 只有目标方法上有@CrmSystemLogging注解修饰的方法，才会有日志环绕
     * @param joinPoint
     */
    @Around("@annotation(crmSystemLogging)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint, CrmSystemLogging crmSystemLogging){

        User loginUser = (User) session.getAttribute("user");

        //新增用户行为日志
        Systemlog systemlog = new Systemlog();
        systemlog.setLogtype(0);//0  普通日志
        systemlog.setNowuser(loginUser.getUserName());//当前用户
        systemlog.setCreatetime(DateTimeUtils.getDateTime()); // 日志时间
        String methodName = joinPoint.getSignature().getName();//目标方法方法名
        systemlog.setMethodName(methodName); //日志的方法

        systemlog.setLogDescription(crmSystemLogging.logDescription()); // 日志描述信息

        Object[] parames = joinPoint.getArgs(); //目标方法如参数
        systemlog.setMethodParms(this.parseParames(parames)); // 方法参数信息
        systemlog.setMethodType(crmSystemLogging.methodType());//方法返回值类型

        Object result = null;
        try {
            System.out.println("目标方法" + methodName + "调用啦!");
            //调用目标方法
            result = joinPoint.proceed();
            //方法返回值信息
            systemlog.setMethodReturn(new ObjectMapper().writeValueAsString(result));//目标方法的返回值
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            //1 异常日志
            systemlog.setLogtype(1);
            //异常信息
            systemlog.setExMessage(throwable.getMessage());
        }
        systemLogService.save(systemlog);
        return result;
    }


    private String parseParames(Object[] parames) {
        if (null == parames || parames.length <= 0) {
            return "";
        }
        StringBuffer param = new StringBuffer("传入参数 # 个:[ ");
        int i = 0;
        for (Object obj : parames) {
            i++;
            if (i == 1) {
                param.append(obj.toString());
                continue;
            }
            param.append(" ,").append(obj.toString());
        }
        return param.append(" ]").toString().replace("#", String.valueOf(i));
    }


}
