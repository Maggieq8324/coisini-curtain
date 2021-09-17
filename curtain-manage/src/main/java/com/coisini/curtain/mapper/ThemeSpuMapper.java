package com.coisini.curtain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coisini.curtain.model.SpuDO;
import com.coisini.curtain.model.ThemeSpuDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-06-03
 */
@Repository
public interface ThemeSpuMapper extends BaseMapper<ThemeSpuDO> {

    /**
     * 获取指定专题下可选spu列表
     * @param id 专题id
     * @return SpuDO
     */
    List<SpuDO> getSimplifySpus(Integer id);

}
