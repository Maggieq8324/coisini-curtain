package com.coisini.curtain.mapper;

import com.coisini.curtain.model.FileModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author pedro@TaleLin
 */
@Repository
public interface FileMapper extends BaseMapper<FileModel> {

    FileModel selectByMd5(@Param("md5") String md5);

    int selectCountByMd5(@Param("md5") String md5);
}
