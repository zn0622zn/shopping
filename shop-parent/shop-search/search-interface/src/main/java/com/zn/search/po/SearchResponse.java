package com.zn.search.po;

import com.zn.shop.item.po.Brand;
import com.zn.shop.item.po.Category;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 搜索结果实体类
 *
 * @author 张男
 * @date: 2023/12/17---12:18
 */
@Data
public class SearchResponse {
    public SearchResponse() {
    }

    public SearchResponse(Long total, Long totalPage, List items, List<Category> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    /**
     * 总行数
     */
    private Long total;
    /**
     * 总页数
     */
    private Long totalPage;
    /**
     * 当前页数据
     */
    private List items;

    /**
     * 相应数据
     */
    private List<Category> categories;
    private List<Brand> brands;
    private List<Map<String, Object>> specs;

}
