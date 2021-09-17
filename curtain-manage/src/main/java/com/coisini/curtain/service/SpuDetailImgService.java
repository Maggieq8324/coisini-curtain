package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.model.SpuDetailImgDO;

public interface SpuDetailImgService extends IService<SpuDetailImgDO> {

    void hardDeleteImgsBySpuId(Integer spuId);

}
