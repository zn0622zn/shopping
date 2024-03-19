package com.zn.controller;

import com.zn.common.utils.DateUtil;
import com.zn.core.controller.BaseController;
import com.zn.po.SeckillGoods;
import com.zn.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/25---16:35
 */
@RestController
@RequestMapping("/seckill-goods")
public class SeckillGoodsController extends BaseController<SeckillGoodsService, SeckillGoods> {
    @Autowired
    private SeckillGoodsService seckillGoodsService;

    /**
     * 返回给前端当前秒杀的五个时间段
     */
    @GetMapping("/menus")
    public List<String> dateMenus() {
        List<Date> dateMenus = DateUtil.getDateMenus();
        ArrayList<String> dates = new ArrayList<>();
        for (Date date : dateMenus) {
            String s = date.toString();
            dates.add(s);
        }
        return dates;
    }

    /**
     * 根据传入的时间段返回要秒杀的商品
     */
    @RequestMapping("/list/{time}")
    public List<SeckillGoods> list(@PathVariable("time") String time) {
        List<SeckillGoods> seckillGoodsList = seckillGoodsService.list(time);
        for (SeckillGoods seckillGoods : seckillGoodsList) {
            System.out.println("id是:" + seckillGoods.getId());
        }
        return seckillGoodsList;
    }

    /**
     * 根据时间段和id查看秒杀的商品
     *
     * @param time 秒杀时间段
     * @param id   id
     * @return 秒杀商品信息
     */
    @GetMapping("/one")
    public SeckillGoods one(String time, Long id) {
        return seckillGoodsService.one(time, id);
    }

    @GetMapping("/delete/{time}/{id}")
    @ResponseBody
    public String delete(@PathVariable String time, @PathVariable String id) {
        seckillGoodsService.deleteSeckillGoodsInRedis(time,id);
        return "删除成功";
    }
}
