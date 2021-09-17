package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.mapper.CUserMapper;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.model.CUserDO;
import com.coisini.curtain.service.CUserService;
import org.springframework.stereotype.Service;

@Service
public class CUserServiceImpl extends ServiceImpl<CUserMapper, CUserDO> implements CUserService {

    @Override
    public IPage<CUserDO> getUserByPage(Integer count, Integer page) {
        Page pager = new Page(page, count);
        IPage<CUserDO> iPage = this.getBaseMapper().selectPage(pager, null);
        return iPage;
    }

    @Override
    public CUserDO getParsedUserById(Integer id) {
        CUserDO user = this.getBaseMapper().selectById(id);
        if (user == null) {
            throw new NotFoundException(120000);
        }
        return user;
    }

    @Override
    public IPage<CUserDO> search(Integer page, Integer count, String keyword) {
        Page<CUserDO> pager = new Page<>(page, count);
        keyword = "".equals(keyword) ? null : "%" + keyword + "%";
        IPage<CUserDO> iPage = this.getBaseMapper().searchCUserByKeyword(pager, keyword);
        return iPage;
    }


}
