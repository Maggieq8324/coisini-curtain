package com.coisini.curtain.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.common.mybatis.Page;
import com.coisini.curtain.model.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @Description Order Mapper 接口
 * @author coisini
 * @date Sep 17, 2021
 * @Version 1.0
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 修改订单状态
     * @param status 状态
     * @param id 订单id
     * @return int
     */
    int changeOrderStatus(@Param("status") Integer status, @Param("id") Integer id);

    /**
     * 搜索订单
     * @param pager 分页对象
     * @param keyword 关键字
     * @param start 开始时间
     * @param end 结束时间
     * @return IPage
     */
    IPage<Order> searchOrders(Page<Order> pager, String keyword, Date start, Date end);

}
