package com.zn.search.po;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;


public class SearchRequest {
    /**
     * 搜索条件
     */
    private String key;
    /**
     * 当前页
     */
    private Integer page;
    /**
     * 根据谁排序
     */
    private String sortBy;
    /**
     * 升序还是降序
     */
    private Boolean descending;
    /**
     * 过滤的条件
     */
    private Map<String, String> filter = new HashMap<>();
    /**
     * 每页大小，固定
     */
    private static final Integer DEFAULT_SIZE = 20;
    /**
     * 默认页
     */
    private static final Integer DEFAULT_PAGE = 1;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if (page == null) {
            return DEFAULT_PAGE;
        }
        //获取页码时检验，不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getDescending() {
        return descending;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }
}
