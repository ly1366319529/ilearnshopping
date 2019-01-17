package com.neuedu.controller.portal;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Order;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 创建订单
     */
    @RequestMapping("createOrder.do")
    public ServerResponse createOrder(HttpSession session,Integer shippingId){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return  ServerResponse.createServerResponseByError("需要登录");
        }
        return orderService.createOrder(userInfo.getId(),shippingId);
    }
    /**
     * 取消订单
     */
    @RequestMapping("cancel.do")
    public ServerResponse cancel(HttpSession session,Long orderNo){
        //防止横向越权
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return orderService.cancel(userInfo.getId(),orderNo);
    }


    /**
     * 获得订单（商品）的订单信息
     */
    @RequestMapping("get_order_cart_product.do")
    public ServerResponse get_order_cart_product(HttpSession session){
        //防止横向越权
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return orderService.get_order_cart_product(userInfo.getId());
    }

    /**
     * 订单列表：分页查询兼容前后台
     */
    @RequestMapping("list.do")
    public ServerResponse list(HttpSession session,   //可传可不传
                               @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        //防止横向越权
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return orderService.list(userInfo.getId(),pageNum,pageSize);
    }

    /**
     * 查询订单详情
     * @param session
     * @return
     */
    @RequestMapping("detail.do")
    public ServerResponse detail(HttpSession session,Long orderNo){
        //防止横向越权
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return orderService.detail(orderNo);
    }


    /**
     * 支付接口
     */
    @RequestMapping("pay.do")
    public  ServerResponse pay(HttpSession session,Long orderNo){
        //防止横向越权
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return  orderService.pay(userInfo.getId(),orderNo);
    }

    /**
     * 支付宝接口回调应用服务器接口
     */
    @RequestMapping("/alipay_callback.do")
    public  ServerResponse callback(HttpServletRequest request){


        System.out.println("=========支付宝接口回调应用服务器接口======");
        //获取支付宝传过来的参数
        Map<String,String[]> parameterMap=request.getParameterMap();
        Map<String,String> requestparams=Maps.newHashMap();
        Iterator<String> iterator=parameterMap.keySet().iterator();
        //遍历数组
        while (iterator.hasNext()){
            String key= iterator.next();
            //根据k获取value值
            String[] strArr=parameterMap.get(key);
            String value="";
            for (int i=0;i<strArr.length;i++){
                //将数组元素转为字符串
                value=(i==strArr.length-1)?value+strArr[i]:value + strArr[i]+",";
            }
            requestparams.put(key,value);
        }
        //支付宝验签
        try {
            requestparams.remove("sign_type");
          boolean result=  AlipaySignature.rsaCheckV2(requestparams,Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
          if (!result){
              return  ServerResponse.createServerResponseByError("非法请求，验证不通过");
          }


        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //处理业务逻辑
        return orderService.alipay_callback(requestparams);
    }




    /**
     * 支付接口
     */
    @RequestMapping("query_order_pay_status.do")
    public  ServerResponse query_order_pay_status(HttpSession session,Long orderNo){
        //防止横向越权
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return  orderService.query_order_pay_status(orderNo);
    }

}
