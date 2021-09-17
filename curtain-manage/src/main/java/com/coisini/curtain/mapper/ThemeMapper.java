package com.coisini.curtain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coisini.curtain.model.SimplifySpu;
import com.coisini.curtain.model.Theme;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-06-03
 */
public interface ThemeMapper extends BaseMapper<Theme> {

    /**
     * 获取主题下的spu
     * @param id 主题id
     * @return spu列表
     */
    List<SimplifySpu> getSpus(@Param("id") Integer id);

}
