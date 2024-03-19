package com.zn.search.dao;

import com.zn.search.po.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 张男
 * @date: 2023/12/16---16:28
 */
public interface GoodsDao extends ElasticsearchRepository<Goods, Long> {

}
