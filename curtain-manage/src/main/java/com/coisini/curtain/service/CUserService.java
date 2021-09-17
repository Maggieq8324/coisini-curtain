package com.coisini.curtain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.model.CUserDO;

public interface CUserService extends IService<CUserDO> {

    CUserDO getParsedUserById(Integer id);

    IPage<CUserDO> getUserByPage(Integer count, Integer page);

    IPage<CUserDO> search(Integer page, Integer count, String keyword);
}
