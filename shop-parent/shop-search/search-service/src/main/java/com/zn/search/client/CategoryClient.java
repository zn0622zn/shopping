package com.zn.search.client;

import com.zn.shop.item.feign.CategoryFeign;
import com.zn.shop.item.po.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/15---19:13
 */
@FeignClient(name = "item-service", fallback = CategoryClient.CategoryClientFallback.class)
public interface CategoryClient extends CategoryFeign {
    @Component
    @RequestMapping("/category-fallback") //这个可以避免容器中requestMapping重复
    class CategoryClientFallback implements CategoryClient {
        private static final Logger LOGGER = LoggerFactory.getLogger(CategoryClientFallback.class);

        @Override
        public List<String> queryNameByIds(List<Long> ids) {
            LOGGER.info("异常发生，进入fallback方法");
            return null;
        }

        @Override
        public List<Category> list(Category category) {
            LOGGER.info("异常发生，进入fallback方法");
            return null;
        }

        @Override
        public Category edit(Long id) {
            LOGGER.info("异常发生，进入fallback方法");
            return null;
        }
    }
}
