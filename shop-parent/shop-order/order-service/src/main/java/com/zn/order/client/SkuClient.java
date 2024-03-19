package com.zn.order.client;

import com.zn.shop.item.feign.SkuFeign;
import com.zn.shop.item.po.Sku;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/23---16:49
 */
@FeignClient(name = "item-service", fallback = SkuClient.SkuClientFallback.class)
public interface SkuClient extends SkuFeign {
    @Component
    @RequestMapping("/sku-fallback")
    //这个可以避免容器中requestMapping重复
    class SkuClientFallback implements SkuClient {
        private static final Logger LOGGER = LoggerFactory.getLogger(SkuClientFallback.class);

        @Override
        public List<Sku> selectSkusBySpuId(Long spuId) {
            LOGGER.error("异常发生，进入fallback方法");
            return null;
        }

        @Override
        public Sku edit(Long id) {
            LOGGER.error("异常发生，进入fallback方法");
            return null;
        }

        @Override
        public int decrCount(Integer num, Long skuId) {
            LOGGER.error("异常发生，进入fallback方法");
            return -1;
        }
    }
}