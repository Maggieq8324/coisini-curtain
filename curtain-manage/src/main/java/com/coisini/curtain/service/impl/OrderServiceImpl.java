package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.common.enumeration.OrderStatusEnum;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.common.util.SensitiveDataUtil;
import com.coisini.curtain.mapper.OrderMapper;
import com.coisini.curtain.model.OrderDO;
import com.coisini.curtain.service.OrderService;
import com.coisini.curtain.vo.OrderSimplifyVO;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements OrderService {

    @Override
    public void changeOrderStatus(Integer id, Integer status) {
        OrderDO order = this.getBaseMapper().selectById(id);
        if (order == null) {
            throw new NotFoundException(110000);
        }
        // 检查订单状态
        if (order.getStatus() != OrderStatusEnum.PAID.getValue() && order.getStatus() != OrderStatusEnum.DELIVERED.getValue()) {
            throw new ForbiddenException(110001);
        }
        if (order.getStatus() == OrderStatusEnum.PAID.getValue()) {
            if (status != OrderStatusEnum.DELIVERED.getValue()) {
                throw new ForbiddenException(110002);
            }
            this.getBaseMapper().changeOrderStatus(status, id);
        }
        if (order.getStatus() == OrderStatusEnum.DELIVERED.getValue()) {
            if (status != OrderStatusEnum.FINISHED.getValue()) {
                throw new ForbiddenException(110003);
            }
            this.getBaseMapper().changeOrderStatus(status, id);
        }
    }

    @Override
    public IPage<OrderDO> getOrderByPage(Integer count, Integer page) {
        Page<OrderDO> pager = new Page<>(page, count);
        QueryWrapper<OrderDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(OrderDO::getId);
        IPage<OrderDO> paging = this.getBaseMapper().selectPage(pager, wrapper);
        return paging;
    }

    @Override
    public IPage<OrderDO> search(Integer page, Integer count, String keyword, Date start, Date end) {
        Page<OrderDO> pager = new Page<>(page, count);
        IPage<OrderDO> paging = this.baseMapper.searchOrders(pager, "%" + keyword + "%", start, end);
        return paging;
    }

    @Override
    public List<OrderSimplifyVO> convertFromDO(List<OrderDO> orders) {
        List<OrderSimplifyVO> orderExpires = new ArrayList<>();
        orders.forEach(order -> {
            Date expireTime = order.getExpiredTime();
            OrderSimplifyVO orderSimplifyVO = new OrderSimplifyVO();
            BeanUtils.copyProperties(order, orderSimplifyVO);
            if (expireTime != null) {
                orderSimplifyVO.setExpired(expireTime.before(new Date()));
            }
            orderSimplifyVO.setPrepayId(SensitiveDataUtil.defaultHide(orderSimplifyVO.getPrepayId()));
            orderExpires.add(orderSimplifyVO);
        });
        return orderExpires;
    }

}
