package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.coisini.curtain.dto.ActivityDTO;
import com.coisini.curtain.mapper.ActivityMapper;
import com.coisini.curtain.mapper.CouponMapper;
import com.coisini.curtain.model.ActivityDO;
import com.coisini.curtain.model.ActivityDetailDO;
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
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, ActivityDO> implements ActivityService {

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public void create(ActivityDTO dto) {
        ActivityDO activity = new ActivityDO();
        BeanUtils.copyProperties(dto, activity);
        this.save(activity);
    }

    @Override
    public void update(ActivityDTO dto, Integer id) {
        ActivityDO activity = this.getById(id);
        if (activity == null) {
            throw new NotFoundException(90000);
        }
        BeanUtils.copyProperties(dto, activity);
        this.updateById(activity);
    }

    @Override
    public ActivityDetailDO getDetailById(Integer id) {
        ActivityDO activity = this.getById(id);
        if (activity == null) {
            throw new NotFoundException(90000);
        }
        List<Integer> coupons = couponMapper.getCouponsByActivityId(id);
        ActivityDetailDO activityDetail = new ActivityDetailDO();
        activityDetail.setCouponIds(coupons);
        BeanUtils.copyProperties(activity, activityDetail);
        return activityDetail;
    }

}
