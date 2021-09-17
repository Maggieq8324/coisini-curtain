package com.coisini.curtain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.dto.CouponDTO;
import com.coisini.curtain.dto.CouponTemplateDTO;
import com.coisini.curtain.model.CouponDO;
import com.coisini.curtain.model.CouponTemplateDO;

import java.util.List;

public interface CouponService extends IService<CouponDO> {

    void create(CouponDTO dto);

    void update(CouponDTO dto, Integer id);

    void delete(Integer id);

    void createTemplate(CouponTemplateDTO dto);

    void updateTemplate(CouponTemplateDTO dto, Integer id);

    CouponTemplateDO getTemplate(Integer id);

    void deleteTemplate(Integer id);

    List<CouponTemplateDO> getTemplates();

    List<CouponDO> getListByActivityId(Integer id);
}
