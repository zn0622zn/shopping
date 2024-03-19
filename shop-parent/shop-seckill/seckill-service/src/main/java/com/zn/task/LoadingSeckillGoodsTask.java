package com.zn.task;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zn.common.utils.DateUtil;
import com.zn.core.po.ResponseBean;
import com.zn.dao.SeckillGoodsDao;
import com.zn.po.SeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author 张男
 * @date: 2023/12/25---13:10
 */
//@Component
@Controller
@RequestMapping("/task")
public class LoadingSeckillGoodsTask {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    /**
     * 定时任务，每隔30S从数据库秒杀商品表中向redis中刷新一次秒杀商品
     */
// @Scheduled(cron = "0/30 * * * * ?")
    @RequestMapping("/load")
    @ResponseBody
    public ResponseBean loadGoodsPushRedis() {
        Jedis resource = jedisPool.getResource();
        //1 获取当前时间后的秒杀时间间隔(5个)
        List<Date> dateMenus = DateUtil.getDateMenus();
        //2 遍历得到的五个时间间隔
        for (Date dateMenu : dateMenus) {
            //3 将每个时间转换格式为一串数字
            String extName = DateUtil.data2str(dateMenu, DateUtil.PATTERN_YYYYMMDDHH);//2024022110
            /*
            select * from seckill_goods_ where
            stock_count_>0
            and status_='1'
            and date_menu_='2023122516'
            and id_ not in(1,2);
             */
            //4 查询当前时间段的秒杀商品，库存大于0，状态审核通过
            QueryWrapper<SeckillGoods> query = Wrappers.<SeckillGoods>query();
            query.gt("stock_count_", 0);//库存大于0
            query.eq("status_", 1);//审核状态为通过
            query.eq("date_menu_", extName);
            //5 拿到redis已经有的秒杀商品的key
            Set<String> timeKeys = resource.hkeys("SeckillGoods-" + extName);
            //6 排除掉redis中已经存在的秒杀商品的id
            if (timeKeys != null && timeKeys.size() > 0) {
                query.notIn("id_", timeKeys);
            }
            //7 从数据库中拿到秒杀商品
            List<SeckillGoods> seckillGoods = seckillGoodsDao.selectList(query);
            //8 将查出来的新的秒杀商品放入redis中
            for (SeckillGoods seckillGood : seckillGoods) {
                resource.hset("SeckillGoods-" + extName, JSON.toJSONString(seckillGood.getId()), JSON.toJSONString(seckillGood));
                resource.expire("SeckillGoods-" + extName, 10800);
            }
        }
        resource.close();
        ResponseBean responseBean = new ResponseBean();
        responseBean.setMessage("秒杀商品加载完毕");
        return responseBean;
    }
}
