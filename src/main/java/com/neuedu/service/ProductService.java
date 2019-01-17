package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    /**
     * 更新商品
     * @param product
     * @return
     */
    ServerResponse saveOrUpdate(Product product);
    public ServerResponse set_sale_status(Integer productId, Integer status);
    /**
     * 后台查看商品详情
     * @param productId
     * @return
     */
    public ServerResponse detail(Integer productId);
    /**
     * 后台-分页查询，商品列表
     */
     public  ServerResponse list(Integer pageNum,Integer pageSize);

    /**
     * 后台-搜索商品
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse search(String productName,Integer productId,Integer pageNum,Integer pageSize);

    /**
     * 图片上传
     * @param file
     * @param path
     * @return
     */
    ServerResponse upload(MultipartFile file,String path);

    ServerResponse detailprotal(Integer productId);

    /**
     * 前台商品页面搜索
     * @param categoryId
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param orderby  排序字段
     * @return
     */
     ServerResponse list(Integer categoryId,String keyword,Integer pageNum,Integer pageSize,String orderby);
}
