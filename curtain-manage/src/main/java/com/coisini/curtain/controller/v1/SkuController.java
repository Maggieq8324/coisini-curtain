package com.coisini.curtain.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.common.util.PageUtil;
import com.coisini.curtain.evt.SkuEvt;
import com.coisini.curtain.model.Sku;
import com.coisini.curtain.model.SkuDetail;
import com.coisini.curtain.service.SkuSpecService;
import com.coisini.curtain.vo.CreatedVo;
import com.coisini.curtain.vo.DeletedVo;
import com.coisini.curtain.vo.UpdatedVo;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.service.SkuService;
import com.coisini.curtain.vo.PageResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/sku")
@Validated
@PermissionModule("SKU")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @Autowired
    private SkuSpecService skuSpecService;

    @PostMapping("")
    @PermissionMeta("创建SKU")
    @GroupRequired
    public CreatedVo create(@RequestBody @Validated SkuEvt evt) {
        skuService.create(evt);
        return new CreatedVo();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新SKU")
    @GroupRequired
    public UpdatedVo update(@RequestBody @Validated SkuEvt evt,
                            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        skuService.update(evt, id);
        return new UpdatedVo();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除SKU")
    public DeletedVo delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        skuService.delete(id);
        return new DeletedVo();
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public SkuDetail getDetail(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        return skuService.getDetail(id);
    }

    @GetMapping("/by/spu/{id}")
    @LoginRequired
    public List<Sku> getBySpuId(@PathVariable(value = "id") @Positive Integer spuId) {
        return this.skuService.lambdaQuery().eq(Sku::getSpuId, spuId).list();
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVo<Sku> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<Sku> pager = new Page<>(page, count);
        IPage<Sku> paging = skuService.getBaseMapper().selectPage(pager, null);
        return PageUtil.build(paging);
    }

    @GetMapping("/spec-value-id")
    @LoginRequired
    public Map<String, Integer> getSpecValueId(
            @RequestParam(name = "key_id", required = false)
            @Positive(message = "{id}") Integer keyId,
            @RequestParam(name = "sku_id", required = false)
            @Positive(message = "{id}") Integer skuId
    ) {
        // 在spu下选择 spec_key 后，在相关 sku 在spec_key下选择 spec_value
        Integer specValueId = skuSpecService.getSpecValueId(keyId, skuId);
        HashMap<String, Integer> result = new HashMap<>(1);
        result.put("value_id", specValueId);
        return result;
    }

}
