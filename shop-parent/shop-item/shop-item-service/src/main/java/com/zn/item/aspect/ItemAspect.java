package com.zn.item.aspect;

import com.zn.core.po.ResponseBean;
import com.zn.item.annotation.Manage;
import com.zn.item.controller.BrandController;
import com.zn.item.utils.RoleUtils;
import com.zn.shop.item.po.Brand;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author 张男
 * @date: 2024/1/27---15:58
 */
//@Component
@Aspect
public class ItemAspect {

    private final HttpServletRequest request;

    @Autowired
    public ItemAspect(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
    }


    /**
     * 所有被Manage注解修饰的方法均被保护
     */
    @Pointcut("@annotation(com.zn.item.annotation.Manage)")
    public void check() {
    }


    @Around("check()")
    public Object around2(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            RoleUtils roleUtils = new RoleUtils();
            Signature signature = proceedingJoinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            Manage manageAnno = method.getAnnotation(Manage.class);
            String roles = request.getHeader("roles");
            boolean result = roleUtils.resolver(manageAnno.value(), roles);
            if (result) {
                return proceedingJoinPoint.proceed();
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
