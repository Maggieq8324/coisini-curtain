package com.coisini.curtain.service;


import com.coisini.curtain.entity.Activity;

/**
 * @Description Activity 接口
 * @author coisini
 * @date Aug 20, 2021
 * @Version 1.0
 */
public interface ActivityService {

    /**
     * getByName
     * @param name
     * @return
     */
    Activity getByName(String name);

}
