package com.coisini.curtain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coisini.curtain.model.SpecKey;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-24
 */
@Repository
public interface SpecKeyMapper extends BaseMapper<SpecKey> {

    /**
     * 根据spuId获取规格键
     * @param spuId spuId
     * @return List
     */
    List<SpecKey> getBySpuId(Integer spuId);

}
