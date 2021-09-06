package com.coisini.curtain.service;

import com.coisini.curtain.entity.User;

import java.util.Map;

/**
 * @Description User 接口
 * @author coisini
 * @date Aug 20, 2021
 * @Version 1.0
 */
public interface UserService {

    /**
     * 通过ID获取用户
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * 更新用户微信信息
     * @param wxUser
     */
    void updateUserWxInfo(Map<String, Object> wxUser);

}
