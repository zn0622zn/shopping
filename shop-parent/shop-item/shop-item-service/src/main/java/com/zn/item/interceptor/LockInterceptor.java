//package com.zn.item.interceptor;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.PrintWriter;
//
///**
// * @author 张男
// * @date: 2024/2/7---15:00
// */
//@Component
//public class LockInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (request.getHeader("lock").equals("false")){
//            return true;
//        }else {
//            response.setHeader("content-type","text/html;charset=utf8");
//            PrintWriter writer = response.getWriter();
//            writer.write("用户以被锁定，无法访问");
//            writer.close();
//            return false;
//        }
//    }
//}
