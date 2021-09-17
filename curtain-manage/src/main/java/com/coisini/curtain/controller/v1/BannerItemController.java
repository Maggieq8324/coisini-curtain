package com.coisini.curtain.controller.v1;

import com.coisini.curtain.evt.BannerItemEvt;
import com.coisini.curtain.service.BannerItemService;
import com.coisini.curtain.vo.CreatedVo;
import com.coisini.curtain.vo.DeletedVo;
import com.coisini.curtain.vo.UpdatedVo;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import com.coisini.curtain.model.BannerItem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/banner-item")
@PermissionModule("Banner item")
public class BannerItemController {

    @Autowired
    private BannerItemService bannerItemService;

    @PostMapping("")
    @PermissionMeta(value = "创建Banner item")
    public CreatedVo create(@Validated @RequestBody BannerItemEvt evt) {
        BannerItem bannerItem = new BannerItem();
        BeanUtils.copyProperties(evt, bannerItem);
        bannerItemService.save(bannerItem);
        return new CreatedVo();
    }

    @PutMapping("/{id}")
    @PermissionMeta(value = "更新Banner item")
    public UpdatedVo update(
            @PathVariable @Positive(message = "{id.positive}") Integer id,
            @Validated @RequestBody BannerItemEvt evt) {
        bannerItemService.update(evt, id);
        return new UpdatedVo();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta(value = "删除Banner item")
    public DeletedVo delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        bannerItemService.delete(id);
        return new DeletedVo();
    }

    @GetMapping("/{id}")
    @PermissionMeta(value = "查询Banner item")
    public BannerItem get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        BannerItem bannerItem = bannerItemService.getById(id);
        if (bannerItem == null) {
            throw new NotFoundException(20001);
        }
        return bannerItem;
    }

}
