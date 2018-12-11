package com.neuedu.controller.manage;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.UserService;
import com.neuedu.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 后台用户控制器类
 */
@RestController
@RequestMapping("/manage/user/")
public class UserManageController {
    @Autowired
    UserService userService;

    /**
     * 管理员登录
     */
    @RequestMapping("login.do")
    public ServerResponse login(HttpSession session,String username,String password){
        ServerResponse serverResponse=userService.login(username,password);
        if (serverResponse.isSuccess()){
            UserInfo userInfo=(UserInfo) serverResponse.getData();
            if (userInfo.getRole()!=Const.USER_ROLE_ADIMN){
                return  ServerResponse.createServerResponseByError("无权限登录");
            }
            session.setAttribute(Const.CURRENTUSER,userInfo);
        }
        return serverResponse;
    }

}
