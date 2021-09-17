package com.coisini.curtain.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.common.util.PageUtil;
import com.coisini.curtain.dto.CouponDTO;
import com.coisini.curtain.dto.CouponTemplateDTO;
import com.coisini.curtain.model.CouponDO;
import com.coisini.curtain.service.CouponService;
import com.coisini.curtain.vo.CreatedVO;
import com.coisini.curtain.vo.DeletedVO;
import com.coisini.curtain.vo.PageResponseVO;
import com.coisini.curtain.vo.UpdatedVO;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.model.CouponTemplateDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;


/**
 * @author TaleLin
 */
@RestController
@RequestMapping("/v1/coupon")
@Validated
@PermissionModule("优惠券")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("")
    @PermissionMeta("创建优惠券")
    @GroupRequired
    public CreatedVO create(@RequestBody @Validated CouponDTO dto) {
        couponService.create(dto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新优惠券")
    @GroupRequired
    public UpdatedVO update(
            @RequestBody @Validated CouponDTO dto,
            @PathVariable @Positive(message = "{id}") Integer id) {
        couponService.update(dto, id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除优惠券")
    @GroupRequired
    public DeletedVO delete(@PathVariable @Positive(message = "{id}") Integer id) {
        couponService.delete(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public CouponDO get(@PathVariable @Positive(message = "{id}") Integer id) {
        CouponDO coupon = couponService.getById(id);
        if (coupon == null) {
            throw new NotFoundException(100000);
        }
        return coupon;
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<CouponDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<CouponDO> pager = new Page<>(page, count);
        IPage<CouponDO> paging = couponService.getBaseMapper().selectPage(pager, null);
        return PageUtil.build(paging);
    }

    @PostMapping("/template")
    @PermissionMeta("创建优惠券模板")
    @GroupRequired
    public CreatedVO createTemplate(@RequestBody @Validated CouponTemplateDTO dto) {
        couponService.createTemplate(dto);
        return new CreatedVO();
    }

    @PutMapping("/template/{id}")
    @PermissionMeta("更新优惠券模板")
    @GroupRequired
    public UpdatedVO updateTemplate(@RequestBody @Validated CouponTemplateDTO dto,
                                    @PathVariable @Positive(message = "{id}") Integer id) {
        couponService.updateTemplate(dto, id);
        return new UpdatedVO();
    }

    @GetMapping("/template/{id}")
    @LoginRequired
    public CouponTemplateDO getTemplate(@PathVariable @Positive(message = "{id}") Integer id) {
        return couponService.getTemplate(id);
    }

    @DeleteMapping("/template/{id}")
    @LoginRequired
    public DeletedVO deleteTemplate(@PathVariable @Positive(message = "{id}") Integer id) {
        couponService.deleteTemplate(id);
        return new DeletedVO();
    }

    @GetMapping("/templates")
    @LoginRequired
    public List<CouponTemplateDO> templates() {
        List<CouponTemplateDO> templates = couponService.getTemplates();
        return templates;
    }

    @GetMapping("/list")
    @LoginRequired
    public List<CouponDO> getListByActivityId(
            @RequestParam(name = "id") @Min(value = 1, message = "{id}") Integer id) {
        List<CouponDO> coupons = couponService.getListByActivityId(id);
        return coupons;
    }

}
