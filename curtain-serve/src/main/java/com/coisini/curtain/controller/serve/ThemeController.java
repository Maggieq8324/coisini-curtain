package com.coisini.curtain.controller.serve;

import com.coisini.curtain.exception.http.NotFoundException;
import com.coisini.curtain.entity.Theme;
import com.coisini.curtain.service.ThemeService;
import com.coisini.curtain.vo.ThemePureVo;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Description Theme控制器
 * @author coisini
 * @date Aug 14, 2021
 * @Version 1.0
 */
@RestController
@RequestMapping("/theme")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    /**
     * 通过names查询
     * @param names
     * @example names=t-1,t-2,t-3
     * @return
     */
    @GetMapping("/by/names")
    public List<ThemePureVo> getThemeGroupByNames(@RequestParam(name = "names") String names) {

        List<String> nameList = Arrays.asList(names.split(","));
        List<Theme> themes = themeService.findByNames(nameList);

        List<ThemePureVo> list = new ArrayList<>();
        themes.forEach(theme -> {
            Mapper mapper = DozerBeanMapperBuilder.buildDefault();
            ThemePureVo vo = mapper.map(theme, ThemePureVo.class);
            list.add(vo);
        });

        return list;
    }

    /**
     * 获取主题spu
     * @param name
     * @return
     */
    @GetMapping("/name/{name}/with_spu")
    public Theme getThemeByNameWithSpu(@PathVariable(name = "name") String name) {
        Optional<Theme> optionalTheme = themeService.findByName(name);
        return optionalTheme.orElseThrow(() -> new NotFoundException(30003));
    }

    /**
     * 获取所有主题
     * @return
     */
    @GetMapping("/getThemes")
    public List<ThemePureVo> getThemes() {
        List<Theme> themes = themeService.getThemes();

        List<ThemePureVo> list = new ArrayList<>();
        themes.forEach(theme -> {
            Mapper mapper = DozerBeanMapperBuilder.buildDefault();
            ThemePureVo vo = mapper.map(theme, ThemePureVo.class);
            list.add(vo);
        });

        return list;
    }

}
