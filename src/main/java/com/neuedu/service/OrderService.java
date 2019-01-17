package com.neuedu.service;

import com.neuedu.common.ServerResponse;

import java.util.Map;

public interface OrderService {
    /**
     * 创建订单
     */
    ServerResponse createOrder(Integer userId,Integer shipping);
    /**
     * 取消订单,根据订单编号取消订单
     */
    ServerResponse cancel(Integer userId,Long orderNo);
    /**
     * 获得订单（商品）的订单信息
     */
    ServerResponse get_order_cart_product(Integer userId);


    ServerResponse list(Integer userId,Integer pageNum,Integer pageSize);

    ServerResponse detail(Long orderNo);
    /**
     * 支付接口
     */
    ServerResponse pay(Integer userId,Long orderNo);

    /**
     *
     */
    ServerResponse alipay_callback(Map<String,String> map);


    ServerResponse query_order_pay_status(Long orderNo);
}
