package com.zn.core.controller;

import com.github.pagehelper.PageInfo;
import com.zn.core.po.BaseEntity;
import com.zn.core.po.ResponseBean;
import com.zn.core.service.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * controller父类
 *
 * @author 张男
 * @date 2023/11/28---14:57
 */
public abstract class BaseController<S extends ICrudService<T>, T extends BaseEntity> {

    @Autowired
    protected S service;

    /**
     * 加载回显->修改时
     */
    @GetMapping("/edit/{id}")
    public T edit(@PathVariable Long id) throws Exception {
        T entity = service.getById(id);//拿到id对应的实体类
        System.err.println(entity+"\t实体类");
        afterEdit(entity);
        return entity;
    }

    /**
     * 分页查询
     */
    @PostMapping("/list-page")
    public PageInfo<T> listPage(
            T entity,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "rows", defaultValue = "10", required = false) int rows
    ) {
        PageInfo<T> result = service.listPage(entity, page, rows);
        return result;
    }


    /**
     *
     * 条件查询
     */
    @RequestMapping("/list")
    public List<T> list(T entity) {
        return service.list(entity);
    }

    /**
     * 实体类有id执行修改，无id执行添加
     */
    @PostMapping("/save")
    public ResponseBean save(T entity) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        try {
            beforeSave(entity); //保存前处理实体类
            service.saveOrUpdate(entity);//调用service层方法
            responseBean.setObject(entity);
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setSuccess(false);
            responseBean.setMessage("保存失败");
        }
        return responseBean;
    }

    /**
     * 删除
     */
    @GetMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        try {
            service.removeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setSuccess(false);
            responseBean.setMessage("保存失败");
        }
        return responseBean;
    }

    /**
     * 批量删除
     */
    @RequestMapping("/delete")
    public ResponseBean delete(@RequestParam List<Long> ids) {
        ResponseBean responseBean = new ResponseBean();
        try {
            service.removeByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setMessage("删除失败");
            responseBean.setSuccess(false);
        }
        return responseBean;
    }

    /**
     * 保存前执行
     */
    public void beforeSave(T entity) throws Exception {
    }

    /**
     * 加载后执行
     */
    public void afterEdit(T entity) {
    }
}
