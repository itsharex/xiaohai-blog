package com.xiaohai.common.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.exception.ServiceException;

import java.util.Objects;

/**
 * @author xiaohai
 * @description: 用户信息工具
 * @date 2024-05-30 23:33
 **/
public class RoleUtils {
    RoleUtils() {
    }

    /**
     * 角色验证
     *
     * @return
     */
    public static boolean checkRole() {
        return !StpUtil.hasRole(Constants.ADMIN) && !StpUtil.hasRole(Constants.DEMO);
    }

    /**
     * 不是当前用户也不是管理员
     *
     * @param userId
     */
    public static void checkActiveUserAndAdmin (Integer userId) {
        if (!Objects.equals(userId, Integer.valueOf((String) StpUtil.getLoginId())) && !StpUtil.hasRole(Constants.ADMIN)) {
            throw new ServiceException("非当前用户数据无法操作");
        }
    }
}
