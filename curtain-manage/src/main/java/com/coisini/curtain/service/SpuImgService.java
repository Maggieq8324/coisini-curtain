package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.model.SpuImg;

public interface SpuImgService extends IService<SpuImg> {

    void hardDeleteImgsBySpuId(Integer spuId);

}
