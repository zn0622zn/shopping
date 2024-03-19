package com.zn.item.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author 张男
 * @date: 2024/1/27---19:18
 */
public class RoleUtils {

    /**
     * 权限字符串的判断
     *
     * @param AnnoManger 访问需要的权限
     * @param DataManger 实际用户的权限
     * @return 是否能够访问
     */
    public boolean resolver(String AnnoManger, String DataManger) {
        int AnnoInt;
        boolean result;
        //实际用的权限在访问需要的权限中
        if (DataManger.contains(AnnoManger)) {
            return true;
        } else {
            //实际用户的权限只要大于访问需要权限的最小权限，即可成功
            AnnoInt = this.changeInt(AnnoManger);
            result = this.splitDataManger(AnnoInt, DataManger);
        }
        return result;
    }

    /**
     * 分解roles字符串，返回能访问此接口的最小权限值
     */
    private boolean splitDataManger(int AnnoInt, String dataManger) {
        String strip = StringUtils.strip(dataManger, "[]");
        String[] roles = strip.split(", ");
        for (String role : roles) {
            int result = this.changeInt(role);
            if (result > AnnoInt) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将字符串权限改为数字
     */
    private int changeInt(String string) {
        if (string.equals("ADMIN")) {
            return 2;
        } else if (string.equals("MANAGER")) {
            return 1;
        } else {
            return 0;
        }
    }
}
