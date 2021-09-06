package com.coisini.curtain.service.impl;

import com.coisini.curtain.entity.Activity;
import com.coisini.curtain.repository.ActivityRepository;
import com.coisini.curtain.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description Activity 实现类
 * @author coisini
 * @date Aug 20, 2021
 * @Version 1.0
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public Activity getByName(String name) {
        return activityRepository.findByName(name);
    }
}
