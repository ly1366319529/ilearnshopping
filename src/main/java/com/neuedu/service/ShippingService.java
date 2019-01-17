package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;

import java.util.List;

public interface ShippingService {

    /**
     * 地址添加
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse add(Integer userId, Shipping shipping);

    /**
     * 地址删除
     * @param userId
     * @param shippingId 地址id
     * @return
     */
    ServerResponse delete(Integer userId, Integer shippingId);

    ServerResponse update(Shipping shipping);

    ServerResponse list(Integer shippingId);

    public  ServerResponse list_select(Integer pageNum, Integer pageSize);
}
