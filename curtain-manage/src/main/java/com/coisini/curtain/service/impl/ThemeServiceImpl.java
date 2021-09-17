package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.evt.ThemeSpuEvt;
import com.coisini.curtain.mapper.ThemeMapper;
import com.coisini.curtain.model.SimplifySpu;
import com.coisini.curtain.model.Theme;
import com.coisini.curtain.model.ThemeSpu;
import com.coisini.curtain.service.ThemeService;
import com.coisini.curtain.mapper.ThemeSpuMapper;
import com.coisini.curtain.model.Spu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeServiceImpl extends ServiceImpl<ThemeMapper, Theme> implements ThemeService {

    @Autowired
    private ThemeSpuMapper themeSpuMapper;

    @Override
    public List<SimplifySpu> getSpus(Integer id) {
        return this.getBaseMapper().getSpus(id);
    }

    @Override
    public void addThemeSpu(ThemeSpuEvt evt) {
        ThemeSpu themeSpu = new ThemeSpu();
        themeSpu.setThemeId(evt.getThemeId());
        themeSpu.setSpuId(evt.getSpuId());
        themeSpuMapper.insert(themeSpu);
    }

    @Override
    public void deleteThemeSpu(Integer id) {
        themeSpuMapper.deleteById(id);
    }

    @Override
    public List<Spu> getSimplifySpus(Integer id) {
        return themeSpuMapper.getSimplifySpus(id);
    }

}
