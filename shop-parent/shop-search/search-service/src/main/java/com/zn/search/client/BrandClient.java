package com.zn.search.client;

import com.zn.shop.item.feign.BrandFeign;
import com.zn.shop.item.po.Brand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "item-service", fallback = BrandClient.BrandClientFallback.class)
public interface BrandClient extends BrandFeign {

    @Component
    @RequestMapping("/brand-fallback") //这个可以避免容器中requestMapping重复
    class BrandClientFallback implements BrandClient {

        private static final Logger LOGGER = LoggerFactory.getLogger(BrandClientFallback.class);

        @Override
        public List<Brand> selectBrandByIds(List<Long> ids) {
            LOGGER.info("异常发生，进入fallback方法");
            return null;
        }
    }
}