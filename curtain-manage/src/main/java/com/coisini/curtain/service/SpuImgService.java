package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.model.SpuImgDO;

public interface SpuImgService extends IService<SpuImgDO> {

    void hardDeleteImgsBySpuId(Integer spuId);

}
