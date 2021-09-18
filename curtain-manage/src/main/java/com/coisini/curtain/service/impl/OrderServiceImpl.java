package com.coisini.curtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.common.enumeration.OrderStatusEnum;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.common.util.SensitiveDataUtil;
import com.coisini.curtain.mapper.OrderMapper;
import com.coisini.curtain.model.Order;
import com.coisini.curtain.service.OrderService;
import com.coisini.curtain.vo.OrderSimplifyVo;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description Order 实现类
 * @author coisini
 * @date Sep 18, 2021
 * @Version 1.0
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void changeOrderStatus(Integer id, Integer status) {
        Order order = this.getBaseMapper().selectById(id);
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
    public IPage<Order> getOrderByPage(Integer count, Integer page) {
        Page<Order> pager = new Page<>(page, count);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(Order::getId);
        IPage<Order> paging = this.getBaseMapper().selectPage(pager, wrapper);
        return paging;
    }

    @Override
    public IPage<Order> search(Integer page, Integer count, String keyword, Date start, Date end) {
        Page<Order> pager = new Page<>(page, count);
        IPage<Order> paging = orderMapper.searchOrders(pager, "%" + keyword + "%", start, end);
        return paging;
    }

    @Override
    public List<OrderSimplifyVo> convertFromDO(List<Order> orders) {
        List<OrderSimplifyVo> orderExpires = new ArrayList<>();
        orders.forEach(order -> {
            Date expireTime = order.getExpiredTime();
            OrderSimplifyVo orderSimplifyVo = new OrderSimplifyVo();
            BeanUtils.copyProperties(order, orderSimplifyVo);
            if (expireTime != null) {
                orderSimplifyVo.setExpired(expireTime.before(new Date()));
            }
            orderSimplifyVo.setPrepayId(SensitiveDataUtil.defaultHide(orderSimplifyVo.getPrepayId()));
            orderExpires.add(orderSimplifyVo);
        });
        return orderExpires;
    }

}
