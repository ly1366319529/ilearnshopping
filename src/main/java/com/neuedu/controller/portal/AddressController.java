package com.neuedu.controller.portal;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping/")
public class AddressController {



    @Autowired
    ShippingService shippingService;

    /**
     * 地址的添加
     * @param session
     * @param shipping
     * @return
     */
    @RequestMapping("add.do")
    public ServerResponse add(HttpSession session, Shipping shipping){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (shipping==null){
            return ServerResponse.createServerResponseByError("请登录");
        }
        return shippingService.add(userInfo.getId(),shipping);
    }

    /**
     * 地址的删除
     * @param session
     * @param shippingId
     * @return
     */
    @RequestMapping("delete.do")
    public ServerResponse delete(HttpSession session, Integer shippingId){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("请登录");
        }
        return shippingService.delete(userInfo.getId(),shippingId);
    }

    @RequestMapping("update.do")
    public  ServerResponse update(HttpSession session, Shipping shipping){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        shipping.setUserId(userInfo.getId());
        return shippingService.update(shipping);
    }

    /**
     * 查看地址
     * @param session
     * @param shippingId
     * @return
     */
    @RequestMapping("list.do")
    public  ServerResponse list(HttpSession session, Integer shippingId){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return shippingService.list(shippingId);
    }

    /**
     * 分页查询
     */
    @RequestMapping("list_select.do")
    public  ServerResponse list_select(HttpSession session,
                                       @RequestParam(required=false,defaultValue = "1") Integer pageNum,
                                       @RequestParam(required=false,defaultValue = "10")Integer pageSize
                                       ) {
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("需要登录");
        }
        return shippingService.list_select(pageNum,pageSize);
    }

    }
