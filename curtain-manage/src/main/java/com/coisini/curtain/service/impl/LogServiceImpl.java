package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.mapper.LogMapper;
import com.coisini.curtain.service.LogService;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.model.Log;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public IPage<Log> getLogPage(Integer page, Integer count, String name, Date start, Date end) {
        Page<Log> pager = new Page<>(page, count);
        IPage<Log> iPage = this.baseMapper.findLogsByUsernameAndRange(pager, name, start, end);
        return iPage;
    }

    @Override
    public IPage<Log> searchLogPage(Integer page, Integer count, String name, String keyword, Date start, Date end) {
        Page<Log> pager = new Page<>(page, count);
        IPage<Log> iPage = this.baseMapper.searchLogsByUsernameAndKeywordAndRange(pager, name, "%" + keyword + "%", start, end);
        return iPage;
    }

    @Override
    public IPage<String> getUserNamePage(Integer page, Integer count) {
        Page<Log> pager = new Page<>(page, count);
        IPage<String> iPage = this.baseMapper.getUserNames(pager);
        return iPage;
    }

    @Override
    public boolean createLog(String message, String permission, Integer userId, String username, String method, String path, Integer status) {
        Log record = Log.builder()
                .message(message)
                .userId(userId)
                .username(username)
                .statusCode(status)
                .method(method)
                .path(path)
                .build();
        if (permission != null) {
            record.setPermission(permission);
        }
        return this.baseMapper.insert(record) > 0;
    }
}
