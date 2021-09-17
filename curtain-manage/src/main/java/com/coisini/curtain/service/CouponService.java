package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.evt.CouponEvt;
import com.coisini.curtain.evt.CouponTemplateEvt;
import com.coisini.curtain.model.Coupon;
import com.coisini.curtain.model.CouponTemplate;

import java.util.List;

public interface CouponService extends IService<Coupon> {

    void create(CouponEvt evt);

    void update(CouponEvt evt, Integer id);

    void delete(Integer id);

    void createTemplate(CouponTemplateEvt evt);

    void updateTemplate(CouponTemplateEvt evt, Integer id);

    CouponTemplate getTemplate(Integer id);

    void deleteTemplate(Integer id);

    List<CouponTemplate> getTemplates();

    List<Coupon> getListByActivityId(Integer id);
}
