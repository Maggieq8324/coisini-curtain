package com.coisini.curtain.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.bo.BannerWithItemsBo;
import com.coisini.curtain.evt.BannerEvt;
import com.coisini.curtain.model.Banner;
import com.coisini.curtain.vo.CreatedVo;
import com.coisini.curtain.vo.DeletedVo;
import com.coisini.curtain.vo.UpdatedVo;
import io.github.talelin.core.annotation.*;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.service.BannerService;
import com.coisini.curtain.vo.PageResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@RequestMapping("/v1/banner")
@RestController
@PermissionModule(value = "Banner")
@Validated
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @PostMapping
    @PermissionMeta(value = "创建Banner")
    @GroupRequired
    public CreatedVo create(@RequestBody @Validated BannerEvt evt) {
        Banner banner = new Banner();
        BeanUtils.copyProperties(evt, banner);
        this.bannerService.save(banner);
        return new CreatedVo();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta(value = "删除Banner")
    @GroupRequired
    public DeletedVo delete(@PathVariable @Positive Integer id) {
        bannerService.delete(id);
        return new DeletedVo();
    }

    @GetMapping("/{id}")
    @LoginRequired
    @PermissionMeta(value = "查询Banner")
    @Logger(template = "{user.username}查询了Banner数据")
    public BannerWithItemsBo getWithItems(@PathVariable @Positive Integer id) {
        return bannerService.getWithItems(id);
    }


    @PutMapping("/{id}")
    @PermissionMeta(value = "更新Banner")
    @GroupRequired
    public UpdatedVo update(@RequestBody @Validated BannerEvt evt,
                            @PathVariable @Positive Integer id) {
        bannerService.update(evt, id);
        return new UpdatedVo();
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVo<Banner> getBanners(@RequestParam(required = false, defaultValue = "0")
                                               @Min(value = 0) Integer page,
                                             @RequestParam(required = false, defaultValue = "10")
                                               @Min(value = 1) @Max(value = 30) Integer count) {

        Page<Banner> pager = new Page<>(page, count);
        IPage<Banner> paging = bannerService.getBaseMapper().selectPage(pager, null);

        return new PageResponseVo<>(paging.getTotal(), paging.getRecords(), paging.getCurrent(), paging.getSize());
    }
}
