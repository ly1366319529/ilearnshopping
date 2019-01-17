package com.neuedu.controller.portal;


import com.alibaba.druid.sql.visitor.functions.Concat;
import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ResponseCodeCategory;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Iterator;

@RestController
@RequestMapping("/portal/product")
public class ProductController {

    @Autowired
    ProductService productService;

    /**
     * 产品的新增or 更新产品
     * @param session
     * @param product
     * @return
     */
    @RequestMapping("/save.do")
    public ServerResponse saveOrUpdate(HttpSession session, Product product){

        //判断用户是否登陆
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }
        //判断用户权限
        if (userInfo.getRole()!=Const.USER_ROLE_ADIMN){
            return ServerResponse.createServerResponseByError(ResponseCodeCategory.NO_PRIVILEGE.getStatus(),ResponseCodeCategory.NO_PRIVILEGE.getMsg());
        }


        return productService.saveOrUpdate(product);
    }
    /**
     * 产品上下架
     */
    @RequestMapping("/set_sale_status.do")
    public ServerResponse set_sale_status(HttpSession session, Integer productId, Integer status){

        //判断用户是否登陆
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }
        //判断用户权限
        if (userInfo.getRole()!=Const.USER_ROLE_ADIMN){
            return ServerResponse.createServerResponseByError(ResponseCodeCategory.NO_PRIVILEGE.getStatus(),ResponseCodeCategory.NO_PRIVILEGE.getMsg());
        }


        return productService.set_sale_status(productId,status);
    }

    /**
     * 查看商品详情
     */
    @RequestMapping("/detail.do")
    public ServerResponse detail(HttpSession session, Integer productId){

        //判断用户是否登陆
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }
        //判断用户权限
        if (userInfo.getRole()!=Const.USER_ROLE_ADIMN){
            return ServerResponse.createServerResponseByError(ResponseCodeCategory.NO_PRIVILEGE.getStatus(),ResponseCodeCategory.NO_PRIVILEGE.getMsg());
        }


        return productService.detail(productId);
    }
    /**
     * 查看商品列表
     */
    @RequestMapping("/list.do")
    public ServerResponse list(HttpSession session,
                                 @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize){

        //判断用户是否登陆
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }
        //判断用户权限
        if (userInfo.getRole()!=Const.USER_ROLE_ADIMN){
            return ServerResponse.createServerResponseByError(ResponseCodeCategory.NO_PRIVILEGE.getStatus(),ResponseCodeCategory.NO_PRIVILEGE.getMsg());
        }


        return productService.list(pageNum,pageSize);
    }
    /**
     * 产品的搜索
     */
    @RequestMapping("/search.do")
    public ServerResponse search(HttpSession session,
                                @RequestParam(value = "productId",required = false )Integer productId,
                                @RequestParam(value = "productName",required = false )String productName,
                               @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                               @RequestParam(value= "pageSize",required = false,defaultValue = "10")Integer pageSize){

        //判断用户是否登陆
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }
        //判断用户权限
        if (userInfo.getRole()!=Const.USER_ROLE_ADIMN){
            return ServerResponse.createServerResponseByError(ResponseCodeCategory.NO_PRIVILEGE.getStatus(),ResponseCodeCategory.NO_PRIVILEGE.getMsg());
        }


        return productService.search(productName,productId,pageNum,pageSize);
    }


    /**
     * 图片上传
     */
/*   @RequestMapping("/search.do")
   public ServerResponse search(HttpSession session){
   return null;
   }*/
}
