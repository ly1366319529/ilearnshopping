package com.neuedu.controller.manage;

import com.neuedu.common.ServerResponse;
import com.neuedu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("manage/product/")
public class ProductManageController {

    @Autowired
    ProductService productService;
    /**
     * 商品详情 根据商品id
     */
    @RequestMapping(value = "/detail.do")
    public ServerResponse detail(Integer productId){
        return productService.detailprotal(productId);
    }

    /**
     * 前台-搜索商品并排序
     */
    @RequestMapping("/list.do")
    public ServerResponse list(@RequestParam(required = false)Integer categoryId,
                               @RequestParam(required = false)String keyword,
                               @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                               @RequestParam(required = false,defaultValue ="" )String orderby){




        return productService.list(categoryId,keyword,pageNum,pageSize,orderby);

    }
}
