package com.zn.search;

import com.zn.search.client.BrandClient;
import com.zn.search.client.CategoryClient;
import com.zn.search.client.SpuClient;
import com.zn.search.dao.GoodsDao;
import com.zn.search.po.Goods;
import com.zn.search.service.IndexService;
import com.zn.shop.item.po.Brand;
import com.zn.shop.item.po.Category;
import com.zn.shop.item.po.Spu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 张男
 * @date: 2023/12/15---16:06
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchApplicationTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private IndexService indexService;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private SpuClient spuClient;

    /**
     * 创建索引表信息(第一次)
     */
    @Test
    public void creatIndexTable() {
        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);
    }

    /**
     * 将所需数据导入es索引库
     */
    @Test
    public void loadData() {
        //拿到所有spu
        List<Spu> spus = spuClient.selectAll();
        //将每一个spu加工为goods
        List<Goods> goods = spus.stream().map(spu -> this.indexService.buildGoods(spu)).collect(Collectors.toList());
        //将全部的goods放入索引库
        goodsDao.saveAll(goods);

    }
}
