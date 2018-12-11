package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;

public interface ProductService {
    /**
     * 更新商品
     * @param product
     * @return
     */
    ServerResponse saveOrUpdate(Product product);
    public ServerResponse set_sale_status(Integer productId, Integer status);
    /**
     * 查看商品详情
     * @param productId
     * @return
     */
    public ServerResponse detail(Integer productId);
    /**
     * 分页查询
     */
     public  ServerResponse list(Integer pageNum,Integer pageSize);

    public ServerResponse search(String productName,Integer productId,Integer pageNum,Integer pageSize);
}
