package com.coisini.curtain.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.model.LogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author pedro@TaleLin
 */
@Repository
public interface LogMapper extends BaseMapper<LogDO> {

    IPage<LogDO> findLogsByUsernameAndRange(Page<LogDO> pager, String name, Date start, Date end);

    IPage<LogDO> searchLogsByUsernameAndKeywordAndRange(Page<LogDO> pager, String name, String keyword, Date start, Date end);

    IPage<String> getUserNames(Page<LogDO> pager);
}
