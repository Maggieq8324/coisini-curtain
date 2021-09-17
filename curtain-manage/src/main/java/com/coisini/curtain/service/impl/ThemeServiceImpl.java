package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.dto.ThemeSpuDTO;
import com.coisini.curtain.mapper.ThemeMapper;
import com.coisini.curtain.model.SimplifySpuDO;
import com.coisini.curtain.model.ThemeDO;
import com.coisini.curtain.model.ThemeSpuDO;
import com.coisini.curtain.service.ThemeService;
import com.coisini.curtain.mapper.ThemeSpuMapper;
import com.coisini.curtain.model.SpuDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeServiceImpl extends ServiceImpl<ThemeMapper, ThemeDO> implements ThemeService {

    @Autowired
    private ThemeSpuMapper themeSpuMapper;

    @Override
    public List<SimplifySpuDO> getSpus(Integer id) {
        return this.getBaseMapper().getSpus(id);
    }

    @Override
    public void addThemeSpu(ThemeSpuDTO dto) {
        ThemeSpuDO themeSpu = new ThemeSpuDO();
        themeSpu.setThemeId(dto.getThemeId());
        themeSpu.setSpuId(dto.getSpuId());
        themeSpuMapper.insert(themeSpu);
    }

    @Override
    public void deleteThemeSpu(Integer id) {
        themeSpuMapper.deleteById(id);
    }

    @Override
    public List<SpuDO> getSimplifySpus(Integer id) {
        return themeSpuMapper.getSimplifySpus(id);
    }

}
