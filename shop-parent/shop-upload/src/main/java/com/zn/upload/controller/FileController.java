package com.zn.upload.controller;

import com.zn.upload.config.FileDfsUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@CrossOrigin //跨域
public class FileController {

    @Resource
    private FileDfsUtil fileDfsUtil ;

    /**
     * http://192.168.64.129/group1/M00/00/00/xxxxxxxxxxxxxxxxxxxxx.png
     */
    @RequestMapping(value = "/uploadFile")
    public ResponseEntity<String> uploadFile (@RequestParam("file") MultipartFile file){
        String result ;
        try{
            String path = fileDfsUtil.upload(file) ;
            if (!StringUtils.isEmpty(path)){
                result = path ;
            } else {
                result = "上传失败" ;
            }
        } catch (Exception e){
            e.printStackTrace() ;
            result = "服务异常" ;
        }
        System.err.println(result);
        return ResponseEntity.ok(result);
    }

    /**
     * 图片删除
     */
    @RequestMapping(value = "/deleteByPath")
    public ResponseEntity<String> deleteByPath (String filePathName){
        fileDfsUtil.deleteFile(filePathName);
        return ResponseEntity.ok("SUCCESS") ;
    }
}