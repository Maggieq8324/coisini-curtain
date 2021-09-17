package com.coisini.curtain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coisini.curtain.model.OrderDO;
import com.coisini.curtain.vo.OrderSimplifyVO;

import java.util.Date;
import java.util.List;

public interface OrderService extends IService<OrderDO> {

    void changeOrderStatus(Integer id, Integer status);

    IPage<OrderDO> getOrderByPage(Integer count, Integer page);

    List<OrderSimplifyVO> convertFromDO(List<OrderDO> orders);

    IPage<OrderDO> search(Integer page, Integer count, String keyword, Date start, Date end);

}
