package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.evt.ActivityEvt;
import com.coisini.curtain.model.Activity;
import com.coisini.curtain.model.ActivityDetail;

public interface ActivityService extends IService<Activity> {

    void create(ActivityEvt evt);

    void update(ActivityEvt evt, Integer id);

    ActivityDetail getDetailById(Integer id);

}
