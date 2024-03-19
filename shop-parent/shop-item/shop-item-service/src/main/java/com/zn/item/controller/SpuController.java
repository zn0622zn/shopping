package com.zn.item.controller;

import com.zn.core.controller.BaseController;
import com.zn.core.po.ResponseBean;
import com.zn.item.service.SpuService;
import com.zn.shop.item.po.Spu;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/13---9:35
 */
@RestController
@RequestMapping("/spu")
public class SpuController extends BaseController<SpuService, Spu> {
    @PostMapping("/save-spu")
    public ResponseBean saveSpu(@RequestBody Spu spu) throws Exception {
        ResponseBean rm = new ResponseBean();
        try {
            service.saveSpu(spu);
        } catch (Exception e) {
            e.printStackTrace();
            rm.setSuccess(false);
            rm.setMessage("保存失败");
        }
        return rm;
    }

    @GetMapping("/list-all")
    public List<Spu> selectAll() {
        return service.list(new Spu());
    }
}
