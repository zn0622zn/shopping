package com.zn.item.controller;

import com.zn.core.controller.BaseController;
import com.zn.item.service.SpuDetailService;
import com.zn.shop.item.po.SpuDetail;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张男
 * @date: 2023/12/13---14:38
 */
@RestController
@RequestMapping("/spu-detail")
public class SpuDetailController extends BaseController<SpuDetailService, SpuDetail> {
}
