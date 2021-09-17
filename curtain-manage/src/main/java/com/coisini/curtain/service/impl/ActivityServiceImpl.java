package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coisini.curtain.evt.ActivityEvt;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.mapper.ActivityMapper;
import com.coisini.curtain.mapper.CouponMapper;
import com.coisini.curtain.model.Activity;
import com.coisini.curtain.model.ActivityDetail;
import com.coisini.curtain.service.ActivityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-20
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public void create(ActivityEvt evt) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(evt, activity);
        this.save(activity);
    }

    @Override
    public void update(ActivityEvt evt, Integer id) {
        Activity activity = this.getById(id);
        if (activity == null) {
            throw new NotFoundException(90000);
        }
        BeanUtils.copyProperties(evt, activity);
        this.updateById(activity);
    }

    @Override
    public ActivityDetail getDetailById(Integer id) {
        Activity activity = this.getById(id);
        if (activity == null) {
            throw new NotFoundException(90000);
        }
        List<Integer> coupons = couponMapper.getCouponsByActivityId(id);
        ActivityDetail activityDetail = new ActivityDetail();
        activityDetail.setCouponIds(coupons);
        BeanUtils.copyProperties(activity, activityDetail);
        return activityDetail;
    }

}
