package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.dto.ThemeSpuDTO;
import com.coisini.curtain.model.SimplifySpuDO;
import com.coisini.curtain.model.ThemeDO;
import com.coisini.curtain.model.SpuDO;

import java.util.List;

public interface ThemeService extends IService<ThemeDO> {

    List<SimplifySpuDO> getSpus(Integer id);

    List<SpuDO> getSimplifySpus(Integer id);

    void addThemeSpu(ThemeSpuDTO dto);

    void deleteThemeSpu(Integer id);

}
