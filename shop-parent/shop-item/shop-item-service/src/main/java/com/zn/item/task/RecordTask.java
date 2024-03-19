package com.zn.item.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author 张男
 * @date: 2024/2/7---18:48
 */
@Component
public class RecordTask {

    /**
     * 点击次数阈值
     */
    private static final int CLICK_THRESHOLD = 10;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 每20min操作一次记录数，记录该用户的个性化信息
     */
    @Scheduled(fixedRate = 1200_000)
    public void checkRecord() {
        //拿个性化brand
        this.getPersonal("brand");
        //拿个性化category
        this.getPersonal("category");
    }


    /**
     * 优先级：五个级别
     * 6个并且全部大于阈值>小于6但是均超过阈值>小于6并且存在超过阈值>6个但是均未超过阈值>小于6并且均未超过阈值
     * 新高于旧，超过阈值高于未超过阈值，大于6高于小于6
     */
    private void getPersonal(String prefix) {
        Jedis resource = jedisPool.getResource();
        Set<String> keys = resource.keys("user-" + prefix + "-*");
        //不同的user下的点击brand次数
        for (String key : keys) {
            //根据score(点击次数)倒序
            Set<String> reBrandId = resource.zrevrange(key, 0, -1);
            //brand的个数
            int number = reBrandId.size();
            //判断用户之前是否存在个性化信息
            String s = resource.get("personal-" + prefix + "-user-" + key.split("-")[2]);
            boolean isFirst = s == null;//true证明之前无个性化信息，false代表之前存在
            //brand访问次数一共没超过6次
            if (number < 6) {
                if (!isFirst) {//如果之前存在个性化信息
                    //判断是否有超过阈值的
                    boolean result = this.checkScore(key, reBrandId, resource);
                    if (result) {//这次要更新的内容有超过阈值的
                        resource.setex("personal-" + prefix + "-user-" + key.split("-")[2], 43200, reBrandId.toString());
                        resource.del(key);
                        continue;
                    } else {//这次更新的内容不仅小于6还没有超过阈值的
                        resource.del(key);
                        continue;
                    }
                } else {//之前无个性化信息
                    String key1 = "personal-" + prefix + "-user-" + key.split("-")[2];
                    resource.setex(key1, 43200, reBrandId.toString());
                    this.addRandom(key1);
                    resource.del(key);
                    continue;
                }
            }
            //如果超过6种
            int i = 0;
            ArrayList<String> moreThanArrayList = new ArrayList<>();
            ArrayList<String> lessThanArrayList = new ArrayList<>();
            for (String brandId : reBrandId) {
                //判断访问的次数是否超过了阈值
                if (this.checkScore(key, brandId, resource)) {
                    //如果超过阈值，加入超过阈值链表
                    moreThanArrayList.add(brandId);
                } else {
                    //加入未超过阈值链表
                    lessThanArrayList.add(brandId);
                }
                if (++i > 5) break;
            }
            //如果有超过阈值的brandId
            if (moreThanArrayList.size() >= 1) {
                resource.setex("personal-" + prefix + "-user-" + key.split("-")[2], 43200, moreThanArrayList.toString());
                resource.del(key);
            } else {
                //虽然满足六种，但是均未超过阈值，返回六种
                if (isFirst) {
                    //如果是第一次加入到个性化推荐列表，否则无操作
                    resource.setex("personal-" + prefix + "-user-" + key.split("-")[2], 43200, lessThanArrayList.toString());
                    resource.del(key);
                }else{
                    resource.del(key);
                }
            }
        }
        resource.close();
    }

    /**
     * 检查brand的set中是否有score超过阈值的
     */
    private boolean checkScore(String key, Set<String> reBrandId, Jedis resource) {
        int flag = 0;
        for (String reBrand : reBrandId) {
            Double zscore = resource.zscore(key, reBrand);
            if (zscore >= CLICK_THRESHOLD) flag = 1;
        }
        return flag == 1;
    }

    /**
     * 检查 reBrandId对应的score是否超过阈值
     */
    private boolean checkScore(String key, String reBrandId, Jedis resource) {
        Double zscore = resource.zscore(key, reBrandId);
        return zscore >= CLICK_THRESHOLD;
    }

    /**
     * 对于不满足6个的，进行随机的添加(或者添加全站热点数据-->维护一个全站点击量内容，添加其中)
     */
    private void addRandom(String key) {

    }
}
