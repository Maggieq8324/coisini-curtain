package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.dto.CouponTemplateDTO;
import com.coisini.curtain.mapper.CouponMapper;
import com.coisini.curtain.model.CouponDO;
import com.coisini.curtain.service.CouponService;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import com.coisini.curtain.common.enumeration.CouponTypeEnum;
import com.coisini.curtain.dto.CouponDTO;
import com.coisini.curtain.mapper.CouponTemplateMapper;
import com.coisini.curtain.model.CouponTemplateDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author TaleLin
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponDO> implements CouponService {

    @Autowired
    private CouponTemplateMapper couponTemplateMapper;

    @Override
    public void create(CouponDTO dto) {
        boolean ok = checkCouponType(dto);
        if (!ok) {
            throw new ParameterException(100001);
        }
        CouponDO coupon = new CouponDO();
        BeanUtils.copyProperties(dto, coupon);
        this.save(coupon);
    }

    @Override
    public void update(CouponDTO dto, Integer id) {
        CouponDO coupon = this.getById(id);
        if (coupon == null) {
            throw new NotFoundException(100000);
        }
        boolean ok = checkCouponType(dto);
        if (!ok) {
            throw new ParameterException(100001);
        }
        BeanUtils.copyProperties(dto, coupon);
        this.updateById(coupon);
    }

    @Override
    public void delete(Integer id) {
        CouponDO coupon = this.getById(id);
        if (coupon == null) {
            throw new NotFoundException(100000);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public List<CouponTemplateDO> getTemplates() {
        return couponTemplateMapper.selectList(null);
    }

    @Override
    public void createTemplate(CouponTemplateDTO dto) {
        boolean ok = checkCouponType(dto);
        if (!ok) {
            throw new ParameterException(100002);
        }
        CouponTemplateDO couponTemplateDO = new CouponTemplateDO();
        BeanUtils.copyProperties(dto, couponTemplateDO);
        couponTemplateMapper.insert(couponTemplateDO);
    }

    @Override
    public void updateTemplate(CouponTemplateDTO dto, Integer id) {
        CouponTemplateDO couponTemplateDO = getTemplate(id);
        boolean ok = checkCouponType(dto);
        if (!ok) {
            throw new ParameterException(100001);
        }
        BeanUtils.copyProperties(dto, couponTemplateDO);
        couponTemplateMapper.updateById(couponTemplateDO);
    }

    @Override
    public CouponTemplateDO getTemplate(Integer id) {
        CouponTemplateDO couponTemplate = couponTemplateMapper.selectById(id);
        if (couponTemplate == null) {
            throw new NotFoundException(100000);
        }
        return couponTemplate;
    }

    @Override
    public void deleteTemplate(Integer id) {
        this.getTemplate(id);
        couponTemplateMapper.deleteById(id);
    }

    @Override
    public List<CouponDO> getListByActivityId(Integer id) {
        QueryWrapper<CouponDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CouponDO::getActivityId, id);
        return this.getBaseMapper().selectList(wrapper);
    }

    /**
     * 校验优惠卷数据是否满足优惠卷类型
     */
    private boolean checkCouponType(CouponTemplateDTO dto) {
        if (dto.getType() == CouponTypeEnum.FULL_MONEY_CUT.getValue()) {
            return (dto.getFullMoney() != null && dto.getMinus() != null);
        } else if (dto.getType() == CouponTypeEnum.DISCOUNT.getValue()) {
            return dto.getDiscount() != null;
        } else if (dto.getType() == CouponTypeEnum.ALL.getValue()) {
            return true;
        } else if (dto.getType() == CouponTypeEnum.FULL_MONEY_DISCOUNT.getValue()) {
            return (dto.getFullMoney() != null && dto.getDiscount() != null);
        } else {
            return false;
        }
    }

}
