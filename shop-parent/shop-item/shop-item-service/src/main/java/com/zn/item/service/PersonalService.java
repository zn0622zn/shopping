package com.zn.item.service;

import com.zn.shop.item.po.Category;
import com.zn.shop.item.po.Spu;

import java.util.List;

/**
 * @author 张男
 * @date: 2024/2/8---10:47
 */
public interface PersonalService {
    List<Category> personalCategory(String uId);

    List<Spu> personalBrandSpu(String uId);
}
