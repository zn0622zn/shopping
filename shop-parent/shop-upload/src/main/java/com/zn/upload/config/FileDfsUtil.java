package com.zn.upload.config;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 图片处理工具类
 */
@Component
public class FileDfsUtil {
    @Resource
    private FastFileStorageClient storageClient;

    /**
     * 上传文件
     */
    public String upload(MultipartFile multipartFile) throws Exception {
        //原始文件的扩展名
        String originalFilename = multipartFile.getOriginalFilename().
                substring(multipartFile.getOriginalFilename().
                        lastIndexOf(".") + 1);
        //存储路径
        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
                multipartFile.getInputStream(),
                multipartFile.getSize(), originalFilename, null);
        return storePath.getFullPath();
    }

    /**
     * 删除文件
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            System.err.println(fileUrl + "路径为空");
            return;
        }
        try {
            //url中解析存储路径
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            //传入组和路径，删除
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            System.err.println("失败");
        }
    }
}