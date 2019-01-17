package com.neuedu.controller.portal;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCodeCategory;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/portal/cart")
public class CartController {

    @Autowired//依赖注入，将对象： 切：分页。体现了切思想
            /**
             * mybatis
             * xml与dao层连接  动态代理
             */
    CartService cartService;

    /**
     * 商品的添加
     * @param session
     * @param productId
     * @param count
     * @return
     */
    @RequestMapping("/add.do")
    public ServerResponse add(HttpSession session,Integer productId, Integer count){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
            return cartService.add(userInfo.getId(),productId,count);
    }

    /**
     * 购物车列表
     */
    @RequestMapping("/list.do")
    public ServerResponse list(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return cartService.list(userInfo.getId());
    }
    /**
     * 更新购物车某个商品的数量
     */
    @RequestMapping("/update.do")
    public ServerResponse update(HttpSession session,Integer productId, Integer count){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return cartService.update(userInfo.getId(),productId,count);
    }

    /**
     * 移除购物车某个产品
     * @param session
     * @return
     */
    @RequestMapping("/delete_product.do")
    public ServerResponse delete_product(HttpSession session,String productIds){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return cartService.delete_product(userInfo.getId(),productIds);
    }

    /**
     * 购物车选中某一商pn
     * @param productId
     * @return
     */
    @RequestMapping("/select_product.do")
    public ServerResponse select_product(HttpSession session,Integer productId){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return cartService.select_product(userInfo.getId(),productId,ResponseCodeCategory.PRODUCT_CHECKEED.getStatus());
    }
    /**
     * 购物车选中某一商pn
     * @param productId
     * @return
     */
    @RequestMapping("/un_select.do")
    public ServerResponse un_select(HttpSession session,Integer productId){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return cartService.select_product(userInfo.getId(),null,ResponseCodeCategory.PRODUCT_UNCHECKEED.getStatus());
    }
    /**
     * 全选
     *
     * @return
     */
    @RequestMapping("/select_all.do")
    public ServerResponse select_all(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return cartService.select_product(userInfo.getId(),null,ResponseCodeCategory.PRODUCT_CHECKEED.getStatus());
    }
    /**
     * 取消全选
     *
     * @return
     */
    @RequestMapping("/un_select_all.do")
    public ServerResponse un_select_all(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return cartService.select_product(userInfo.getId(),null,ResponseCodeCategory.PRODUCT_UNCHECKEED.getStatus());
    }
    /**
     * 查询购物车产品的数量
     */
    @RequestMapping("/get_cart_product_count.do")
    public ServerResponse get_cart_product_count(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return cartService.get_cart_product_count(userInfo.getId());
    }
}
