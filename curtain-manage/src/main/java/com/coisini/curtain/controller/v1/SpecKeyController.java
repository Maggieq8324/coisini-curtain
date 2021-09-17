package com.coisini.curtain.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.bo.SpecKeyAndItemsBo;
import com.coisini.curtain.common.util.PageUtil;
import com.coisini.curtain.evt.SpecKeyEvt;
import com.coisini.curtain.model.SpecKey;
import com.coisini.curtain.vo.CreatedVo;
import com.coisini.curtain.vo.DeletedVo;
import com.coisini.curtain.vo.UpdatedVo;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.service.SpecKeyService;
import com.coisini.curtain.vo.PageResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author generator@TaleLin
 * @since 2020-05-24
 */
@RestController
@RequestMapping("/v1/spec-key")
@PermissionModule("规格名")
@Validated
public class SpecKeyController {

    @Autowired
    private SpecKeyService specKeyService;

    @PostMapping("")
    @PermissionMeta(value = "创建规格名")
    @GroupRequired
    public CreatedVo create(@Validated @RequestBody SpecKeyEvt evt) {
        specKeyService.create(evt);
        return new CreatedVo();
    }

    @PutMapping("/{id}")
    @PermissionMeta(value = "更新规格名")
    @GroupRequired
    public UpdatedVo update(
            @Validated @RequestBody SpecKeyEvt evt,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        specKeyService.update(evt, id);
        return new UpdatedVo();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta(value = "删除规格名")
    @GroupRequired
    public DeletedVo delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        specKeyService.delete(id);
        return new DeletedVo();
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public SpecKeyAndItemsBo detail(@PathVariable @Positive(message = "{id}") Integer id) {
        SpecKeyAndItemsBo specKeyAndItems = specKeyService.getKeyAndValuesById(id);
        return specKeyAndItems;
    }

    @GetMapping("/by/spu/{id}")
    public List<SpecKey> getBySpuId(@PathVariable(value = "id") @Positive Integer spuId) {
        return this.specKeyService.getBySpuId(spuId);
    }

    @GetMapping("/page")
    public PageResponseVo<SpecKey> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<SpecKey> pager = new Page<>(page, count);
        IPage<SpecKey> paging = specKeyService.getBaseMapper().selectPage(pager, null);
        return PageUtil.build(paging);
    }

    @GetMapping("/list")
    @LoginRequired
    public List<SpecKey> getList() {
        return specKeyService.list();
    }

}
