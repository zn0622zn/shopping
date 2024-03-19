package com.zn.order.client;


import com.zn.feign.UserFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;


@FeignClient(name = "login-service", fallback = UserClient.UserClientFallback.class)
public interface UserClient extends UserFeign {

    @Component
    @RequestMapping("/fallback") //这个可以避免容器中requestMapping重复
    class UserClientFallback implements UserClient {
        @Override
        public String addPoint(Long point, String username) {
            LOGGER.info("异常发生，进入fallback方法");
            return null;
        }
    }

}
