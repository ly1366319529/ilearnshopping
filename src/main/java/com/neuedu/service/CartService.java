package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;

public interface CartService {

    ServerResponse add(Integer userId,Integer productId,Integer count);

    /**
     *购物车列表
     */
    ServerResponse list(Integer userId);

    /**
     * 更新购物车商品数量
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerResponse update(Integer userId,Integer productId, Integer count);

    /**
     * 删除购物车中的某个商品
     * @param userId
     * @param productIds
     * @return
     */
    ServerResponse delete_product(Integer userId,String productIds);
    /**
     * 购物车选中某一商品
     * @param userId
     * @param productId
     * @return
     */

    ServerResponse select_product(Integer userId,Integer productId,Integer check);
    /**
     * 查询购物车产品的数量
     */
    ServerResponse get_cart_product_count(Integer userId);

}
