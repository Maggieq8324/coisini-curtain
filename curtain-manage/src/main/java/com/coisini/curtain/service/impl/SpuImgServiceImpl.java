package com.coisini.curtain.service.impl;

import com.coisini.curtain.model.SpuImgDO;
import com.coisini.curtain.mapper.SpuImgMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.service.SpuImgService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-27
 */
@Service
public class SpuImgServiceImpl extends ServiceImpl<SpuImgMapper, SpuImgDO> implements SpuImgService {

    @Override
    public void hardDeleteImgsBySpuId(Integer spuId) {
        this.getBaseMapper().hardDeleteImgsBySpuId(spuId);
    }
}
