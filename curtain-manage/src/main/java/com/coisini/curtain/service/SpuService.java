package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.dto.SpuDTO;
import com.coisini.curtain.model.SpuDO;
import com.coisini.curtain.model.SpuDetailDO;

import java.util.List;

/**
 * @Description Spu 接口
 * @author coisini
 * @date Sep 8, 2021
 * @Version 1.0
 */
public interface SpuService extends IService<SpuDO> {

    void create(SpuDTO dto);

    void update(SpuDTO dto, Integer id);

    void delete(Integer id);

    SpuDetailDO getDetail(Integer id);

    List<Integer> getSpecKeys(Integer id);

}
