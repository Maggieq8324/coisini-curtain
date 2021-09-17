package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.dto.ActivityDTO;
import com.coisini.curtain.model.ActivityDO;
import com.coisini.curtain.model.ActivityDetailDO;

public interface ActivityService extends IService<ActivityDO> {

    void create(ActivityDTO dto);

    void update(ActivityDTO dto, Integer id);

    ActivityDetailDO getDetailById(Integer id);

}
