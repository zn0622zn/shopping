package com.zn.search.service;

import com.zn.search.client.BrandClient;
import com.zn.search.client.CategoryClient;
import com.zn.search.client.SpecParamClient;
import com.zn.search.dao.GoodsDao;
import com.zn.search.po.Goods;
import com.zn.search.po.SearchRequest;
import com.zn.search.po.SearchResponse;
import com.zn.shop.item.po.Brand;
import com.zn.shop.item.po.Category;
import com.zn.shop.item.po.SpecParam;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张男
 * @date: 2023/12/17---12:40
 */
@Service
public class SearchService {
    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SpecParamClient specParamClient;

    public SearchResponse search(SearchRequest searchRequest) {
        //搜索条件
        String key = searchRequest.getKey();
        if (key == null) {
            return null;
        }

        //查询构建工具
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //设置过滤字段，只返回设置的字段
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "subTitle", "skus"}, null));

        //分页
        Integer myPage = searchRequest.getPage() - 1;//第一页从0开始
        Integer size = searchRequest.getSize();
        nativeSearchQueryBuilder.withPageable(PageRequest.of(myPage, size));

        //排序
        //获取排序的条件
        String sortBy = searchRequest.getSortBy();
        Boolean desc = searchRequest.getDescending();
        if (StringUtils.isNotBlank(sortBy)) {
            //把排序条件加给构建器
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));
        }


        //构建过滤条件和基本查询条件
        BoolQueryBuilder boolQueryBuilder = buildBasicQueryWithFilter(searchRequest);
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);

        //添加聚合条件
        String brandAggsName = "brands";
        String categoryAggsName = "categorys";
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(brandAggsName).field("brandId"));
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(categoryAggsName).field("cid3"));

        //得到聚合
        AggregatedPage<Goods> page = (AggregatedPage<Goods>) goodsDao.search(nativeSearchQueryBuilder.build());

        //处理聚合
        List<Brand> brands = getBrandAgg(page, brandAggsName);
        List<Category> categorys = getCategoryAgg(page, categoryAggsName);
        List<Map<String, Object>> specs = null;
        if (categorys.size() == 1) {
            specs = getSpecs(categorys.get(0).getId(), boolQueryBuilder);
        }

        //分页数据
        long total = page.getTotalElements();//总行数
        long totalPages = page.getTotalPages();//总页数

        return new SearchResponse(total, totalPages, page.getContent(), categorys, brands, specs);

    }

    /**
     * 处理品牌聚合数据
     */
    private List<Brand> getBrandAgg(AggregatedPage<Goods> page, String brandAggsName) {
        LongTerms longTerms = (LongTerms) page.getAggregation(brandAggsName);
        ArrayList<Long> brandIds = new ArrayList<>();
        for (LongTerms.Bucket bucket : longTerms.getBuckets()) {
            brandIds.add(bucket.getKeyAsNumber().longValue());
        }
        return brandClient.selectBrandByIds(brandIds);
    }

    /**
     * 处理分类聚合数据
     */
    private List<Category> getCategoryAgg(AggregatedPage<Goods> page, String categoryAggsName) {
        LongTerms longTerms = (LongTerms) page.getAggregation(categoryAggsName);
        List<Long> categoryIds = new ArrayList<>();
        for (LongTerms.Bucket bucket : longTerms.getBuckets()) {
            categoryIds.add(bucket.getKeyAsNumber().longValue());
        }
        List<String> names = this.categoryClient.queryNameByIds(categoryIds);
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            Category category = new Category();
            category.setId(categoryIds.get(i));
            category.setTitle(names.get(i));
            categories.add(category);
        }
        return categories;
    }

    /**
     *
     */
    private List<Map<String, Object>> getSpecs(Long cid, QueryBuilder query) {
        List<Map<String, Object>> specs = new ArrayList<>();

        SpecParam sp = new SpecParam();
        sp.setCid(cid);
        sp.setSearching(true);
        List<SpecParam> specParams = this.specParamClient.selectSpecParamApi(sp);
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(query);
        for (SpecParam specParam : specParams) {
            String name = specParam.getName();//内存，产地
            queryBuilder.addAggregation(AggregationBuilders.terms(name).field("specs." + name + ".keyword"));
        }
        AggregatedPage<Goods> aggs = (AggregatedPage<Goods>) this.goodsDao.search(queryBuilder.build());
        Map<String, Aggregation> stringAggregationMap =
                aggs.getAggregations().asMap();
        for (SpecParam specParam : specParams) {
            Map<String, Object> spec = new HashMap<>();
            String name = specParam.getName();
            if (stringAggregationMap.get(name) instanceof StringTerms) {
                StringTerms stringTerms = (StringTerms) stringAggregationMap.get(name);
                List<String> val = new ArrayList<>();
                for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
                    val.add(bucket.getKeyAsString());
                }
                spec.put("k", name);//内存，存储空间，屏幕尺寸
                spec.put("options", val);
                specs.add(spec);
            }
        }
        return specs;
    }

    private BoolQueryBuilder buildBasicQueryWithFilter(SearchRequest searchRequest) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchQuery("all", searchRequest.getKey()));
        BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();
        for (Map.Entry<String, String> entry : searchRequest.getFilter().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key != "cid3" && key != "brandId") {
                key = "specs." + key + ".keyword";
            }
            filterQueryBuilder.must(QueryBuilders.termQuery(key, value));
        }
        return queryBuilder.filter(filterQueryBuilder);
    }
}
