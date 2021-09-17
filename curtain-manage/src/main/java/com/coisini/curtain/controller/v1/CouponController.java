package com.coisini.curtain.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.common.util.PageUtil;
import com.coisini.curtain.evt.CouponEvt;
import com.coisini.curtain.evt.CouponTemplateEvt;
import com.coisini.curtain.model.Coupon;
import com.coisini.curtain.service.CouponService;
import com.coisini.curtain.vo.CreatedVo;
import com.coisini.curtain.vo.DeletedVo;
import com.coisini.curtain.vo.PageResponseVo;
import com.coisini.curtain.vo.UpdatedVo;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.model.CouponTemplate;
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
    public CreatedVo create(@RequestBody @Validated CouponEvt evt) {
        couponService.create(evt);
        return new CreatedVo();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新优惠券")
    @GroupRequired
    public UpdatedVo update(
            @RequestBody @Validated CouponEvt evt,
            @PathVariable @Positive(message = "{id}") Integer id) {
        couponService.update(evt, id);
        return new UpdatedVo();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除优惠券")
    @GroupRequired
    public DeletedVo delete(@PathVariable @Positive(message = "{id}") Integer id) {
        couponService.delete(id);
        return new DeletedVo();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public Coupon get(@PathVariable @Positive(message = "{id}") Integer id) {
        Coupon coupon = couponService.getById(id);
        if (coupon == null) {
            throw new NotFoundException(100000);
        }
        return coupon;
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVo<Coupon> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<Coupon> pager = new Page<>(page, count);
        IPage<Coupon> paging = couponService.getBaseMapper().selectPage(pager, null);
        return PageUtil.build(paging);
    }

    @PostMapping("/template")
    @PermissionMeta("创建优惠券模板")
    @GroupRequired
    public CreatedVo createTemplate(@RequestBody @Validated CouponTemplateEvt evt) {
        couponService.createTemplate(evt);
        return new CreatedVo();
    }

    @PutMapping("/template/{id}")
    @PermissionMeta("更新优惠券模板")
    @GroupRequired
    public UpdatedVo updateTemplate(@RequestBody @Validated CouponTemplateEvt evt,
                                    @PathVariable @Positive(message = "{id}") Integer id) {
        couponService.updateTemplate(evt, id);
        return new UpdatedVo();
    }

    @GetMapping("/template/{id}")
    @LoginRequired
    public CouponTemplate getTemplate(@PathVariable @Positive(message = "{id}") Integer id) {
        return couponService.getTemplate(id);
    }

    @DeleteMapping("/template/{id}")
    @LoginRequired
    public DeletedVo deleteTemplate(@PathVariable @Positive(message = "{id}") Integer id) {
        couponService.deleteTemplate(id);
        return new DeletedVo();
    }

    @GetMapping("/templates")
    @LoginRequired
    public List<CouponTemplate> templates() {
        List<CouponTemplate> templates = couponService.getTemplates();
        return templates;
    }

    @GetMapping("/list")
    @LoginRequired
    public List<Coupon> getListByActivityId(
            @RequestParam(name = "id") @Min(value = 1, message = "{id}") Integer id) {
        List<Coupon> coupons = couponService.getListByActivityId(id);
        return coupons;
    }

}
