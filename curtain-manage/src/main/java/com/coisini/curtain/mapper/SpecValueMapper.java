package com.coisini.curtain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coisini.curtain.model.SpecValue;
import com.coisini.curtain.model.SpecKeyValue;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-24
 */
@Repository
public interface SpecValueMapper extends BaseMapper<SpecValue> {

    /**
     * 根据规格名id和规格值id，获取规格名和规格值
     * @param keyId 规格名id
     * @param valueId 规格值id
     * @return SpecKeyValueDO
     */
    SpecKeyValue getSpecKeyAndValueById(Integer keyId, Integer valueId);

}
