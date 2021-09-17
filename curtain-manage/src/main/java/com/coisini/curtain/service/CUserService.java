package com.coisini.curtain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.model.ClientUser;

public interface CUserService extends IService<ClientUser> {

    ClientUser getParsedUserById(Integer id);

    IPage<ClientUser> getUserByPage(Integer count, Integer page);

    IPage<ClientUser> search(Integer page, Integer count, String keyword);
}
