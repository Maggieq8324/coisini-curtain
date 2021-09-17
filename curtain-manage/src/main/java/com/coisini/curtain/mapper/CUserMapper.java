package com.coisini.curtain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.model.ClientUser;

/**
 * @author TaleLin
 */
public interface CUserMapper extends BaseMapper<ClientUser> {

    /**
     * 模糊查询C端用户
     * @param pager   分页对象
     * @param keyword 关键字
     * @return IPage
     */
    IPage<ClientUser> searchCUserByKeyword(Page<ClientUser> pager, String keyword);

}
