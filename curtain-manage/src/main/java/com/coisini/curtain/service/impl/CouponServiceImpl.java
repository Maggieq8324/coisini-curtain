package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.evt.CouponTemplateEvt;
import com.coisini.curtain.mapper.CouponMapper;
import com.coisini.curtain.model.Coupon;
import com.coisini.curtain.service.CouponService;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import com.coisini.curtain.common.enumeration.CouponTypeEnum;
import com.coisini.curtain.evt.CouponEvt;
import com.coisini.curtain.mapper.CouponTemplateMapper;
import com.coisini.curtain.model.CouponTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author TaleLin
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

    @Autowired
    private CouponTemplateMapper couponTemplateMapper;

    @Override
    public void create(CouponEvt evt) {
        boolean ok = checkCouponType(evt);
        if (!ok) {
            throw new ParameterException(100001);
        }
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(evt, coupon);
        this.save(coupon);
    }

    @Override
    public void update(CouponEvt evt, Integer id) {
        Coupon coupon = this.getById(id);
        if (coupon == null) {
            throw new NotFoundException(100000);
        }
        boolean ok = checkCouponType(evt);
        if (!ok) {
            throw new ParameterException(100001);
        }
        BeanUtils.copyProperties(evt, coupon);
        this.updateById(coupon);
    }

    @Override
    public void delete(Integer id) {
        Coupon coupon = this.getById(id);
        if (coupon == null) {
            throw new NotFoundException(100000);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public List<CouponTemplate> getTemplates() {
        return couponTemplateMapper.selectList(null);
    }

    @Override
    public void createTemplate(CouponTemplateEvt evt) {
        boolean ok = checkCouponType(evt);
        if (!ok) {
            throw new ParameterException(100002);
        }
        CouponTemplate couponTemplate = new CouponTemplate();
        BeanUtils.copyProperties(evt, couponTemplate);
        couponTemplateMapper.insert(couponTemplate);
    }

    @Override
    public void updateTemplate(CouponTemplateEvt evt, Integer id) {
        CouponTemplate couponTemplate = getTemplate(id);
        boolean ok = checkCouponType(evt);
        if (!ok) {
            throw new ParameterException(100001);
        }
        BeanUtils.copyProperties(evt, couponTemplate);
        couponTemplateMapper.updateById(couponTemplate);
    }

    @Override
    public CouponTemplate getTemplate(Integer id) {
        CouponTemplate couponTemplate = couponTemplateMapper.selectById(id);
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
    public List<Coupon> getListByActivityId(Integer id) {
        QueryWrapper<Coupon> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Coupon::getActivityId, id);
        return this.getBaseMapper().selectList(wrapper);
    }

    /**
     * 校验优惠卷数据是否满足优惠卷类型
     */
    private boolean checkCouponType(CouponTemplateEvt evt) {
        if (evt.getType() == CouponTypeEnum.FULL_MONEY_CUT.getValue()) {
            return (evt.getFullMoney() != null && evt.getMinus() != null);
        } else if (evt.getType() == CouponTypeEnum.DISCOUNT.getValue()) {
            return evt.getDiscount() != null;
        } else if (evt.getType() == CouponTypeEnum.ALL.getValue()) {
            return true;
        } else if (evt.getType() == CouponTypeEnum.FULL_MONEY_DISCOUNT.getValue()) {
            return (evt.getFullMoney() != null && evt.getDiscount() != null);
        } else {
            return false;
        }
    }

}
