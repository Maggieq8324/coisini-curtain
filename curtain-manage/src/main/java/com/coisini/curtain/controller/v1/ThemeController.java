package com.coisini.curtain.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.evt.ThemeSpuEvt;
import com.coisini.curtain.model.SimplifySpu;
import com.coisini.curtain.model.Spu;
import com.coisini.curtain.model.Theme;
import com.coisini.curtain.service.ThemeService;
import com.coisini.curtain.vo.CreatedVo;
import com.coisini.curtain.vo.DeletedVo;
import com.coisini.curtain.vo.UpdatedVo;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.*;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.common.util.PageUtil;
import com.coisini.curtain.evt.ThemeEvt;
import com.coisini.curtain.vo.PageResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/theme")
@Validated
@PermissionModule("主题")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @PostMapping("")
    @PermissionMeta("创建主题")
    @GroupRequired
    public CreatedVo create(@Validated @RequestBody ThemeEvt evt) {
        Theme theme = new Theme();
        BeanUtils.copyProperties(evt, theme);
        themeService.save(theme);
        return new CreatedVo();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新主题")
    @GroupRequired
    public UpdatedVo update(
            @Validated @RequestBody ThemeEvt evt,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        Theme theme = themeService.getById(id);
        if (theme == null) {
            throw new NotFoundException(30000);
        }
        BeanUtils.copyProperties(evt, theme);
        themeService.updateById(theme);
        return new UpdatedVo();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除主题")
    @GroupRequired
    public DeletedVo delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        Theme theme = themeService.getById(id);
        if (theme == null) {
            throw new NotFoundException(30000);
        }
        themeService.getBaseMapper().deleteById(id);
        return new DeletedVo();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public Theme get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        Theme theme = themeService.getById(id);
        if (theme == null) {
            throw new NotFoundException(30000);
        }
        return theme;
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVo<Theme> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<Theme> pager = new Page<>(page, count);
        IPage<Theme> paging = themeService.getBaseMapper().selectPage(pager, null);
        return PageUtil.build(paging);
    }

    /**
     * 选择 theme/spus?id=1 作为 规则
     * 而没有选择 theme/1/spus 作为路由规则的主要原因是
     * theme下的spus以后可能会通过其它的属性进行筛选，例如 name
     */
    @GetMapping("/spus")
    @LoginRequired
    public List<SimplifySpu> getSpus(@RequestParam(name = "id") @Positive(message = "{id}") Integer id) {
        return themeService.getSpus(id);
    }

    @GetMapping("/spu/list")
    @LoginRequired
    public List<Spu> getSpuList(@RequestParam(name = "id") @Positive(message = "{id}") Integer id) {
        return themeService.getSimplifySpus(id);
    }

    @PostMapping("/spu")
    @PermissionMeta("创建专题下的spu")
    @GroupRequired
    public CreatedVo addThemeSpu(@RequestBody @Validated ThemeSpuEvt evt) {
        themeService.addThemeSpu(evt);
        return new CreatedVo();
    }

    @DeleteMapping("/spu/{id}")
    @PermissionMeta("删除专题下的spu")
    @GroupRequired
    public DeletedVo deleteThemeSpu(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        themeService.deleteThemeSpu(id);
        return new DeletedVo();
    }

}
