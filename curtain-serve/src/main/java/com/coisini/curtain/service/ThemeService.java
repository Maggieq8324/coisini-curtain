package com.coisini.curtain.service;

import com.coisini.curtain.entity.Theme;

import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @author coisini
 * @date
 * @Version 1.0
 */
public interface ThemeService {

    /**
     * 通过名称查找
     * @param nameList
     * @return
     */
    List<Theme> findByNames(List<String> nameList);

    /**
     * 通过名称查找
     * @param name
     * @return
     */
    Optional<Theme> findByName(String name);

    /**
     * 获取所有主题
     * @return
     */
    List<Theme> getThemes();
}
