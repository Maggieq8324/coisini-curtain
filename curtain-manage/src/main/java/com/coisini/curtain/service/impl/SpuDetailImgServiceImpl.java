package com.coisini.curtain.service.impl;

import com.coisini.curtain.mapper.SpuDetailImgMapper;
import com.coisini.curtain.service.SpuDetailImgService;
import com.coisini.curtain.model.SpuDetailImgDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SpuDetailImgServiceImpl extends ServiceImpl<SpuDetailImgMapper, SpuDetailImgDO> implements SpuDetailImgService {

    @Override
    public void hardDeleteImgsBySpuId(Integer spuId) {
        this.getBaseMapper().hardDeleteImgsBySpuId(spuId);
    }
}
