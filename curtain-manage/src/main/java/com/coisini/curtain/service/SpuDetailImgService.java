package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.model.SpuDetailImg;

public interface SpuDetailImgService extends IService<SpuDetailImg> {

    void hardDeleteImgsBySpuId(Integer spuId);

}
