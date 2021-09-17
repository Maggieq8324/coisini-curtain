package com.coisini.curtain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.model.Order;
import com.coisini.curtain.vo.OrderSimplifyVo;

import java.util.Date;
import java.util.List;

public interface OrderService extends IService<Order> {

    void changeOrderStatus(Integer id, Integer status);

    IPage<Order> getOrderByPage(Integer count, Integer page);

    List<OrderSimplifyVo> convertFromDO(List<Order> orders);

    IPage<Order> search(Integer page, Integer count, String keyword, Date start, Date end);

}
