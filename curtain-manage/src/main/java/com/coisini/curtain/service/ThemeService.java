package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.evt.ThemeSpuEvt;
import com.coisini.curtain.model.SimplifySpu;
import com.coisini.curtain.model.Theme;
import com.coisini.curtain.model.Spu;

import java.util.List;

public interface ThemeService extends IService<Theme> {

    List<SimplifySpu> getSpus(Integer id);

    List<Spu> getSimplifySpus(Integer id);

    void addThemeSpu(ThemeSpuEvt evt);

    void deleteThemeSpu(Integer id);

}
