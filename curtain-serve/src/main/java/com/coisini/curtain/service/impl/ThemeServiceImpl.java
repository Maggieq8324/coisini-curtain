package com.coisini.curtain.service.impl;

import com.coisini.curtain.entity.Theme;
import com.coisini.curtain.repository.ThemeRepository;
import com.coisini.curtain.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @author coisini
 * @date
 * @Version 1.0
 */
@Service
public class ThemeServiceImpl implements ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    @Override
    public List<Theme> findByNames(List<String> names) {
        return themeRepository.findByNames(names);
    }

    @Override
    public Optional<Theme> findByName(String name) {
        return themeRepository.findByName(name);
    }

    @Override
    public List<Theme> getThemes() {
        return themeRepository.findAll();
    }


}
