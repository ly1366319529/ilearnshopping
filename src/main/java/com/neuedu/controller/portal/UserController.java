package com.neuedu.controller.portal;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.UserService;
import com.neuedu.utils.MD5Utils;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * controller层返回对象类型
 */
@RestController
@RequestMapping("/portal/user")
public class UserController {


    @Autowired
    UserService userService;

    @RequestMapping("/login.do")
    public ServerResponse login(HttpSession httpSession,String username, String password){
        ServerResponse serverResponse=userService.login(username,password);
        if (serverResponse.isSuccess()){//保存登录状态
            httpSession.setAttribute(Const.CURRENTUSER,serverResponse.getData());
        }
        return serverResponse;
    }

    /**
     *注册接口
     * @param userInfo
     * @return
     */
    @RequestMapping("/register.do")
    public ServerResponse register(UserInfo userInfo){

        return userService.register(userInfo);
    }

    /**
     * 检查用户名或邮箱是否有效
     */
    @RequestMapping("/check_valid.do")
    public  ServerResponse check_valid(String str,String type){
        return userService.check_valid(str,type);
    }

    /**
     * 获取用户详细信息
     */
    @RequestMapping("/get_user_info.do")
    public ServerResponse get_user_info(HttpSession session){
        Object o=session.getAttribute(Const.CURRENTUSER);
        if (o != null && o instanceof UserInfo){
            UserInfo userInfo=(UserInfo)o;
            //前端响应的用户
            UserInfo response_UserInfo=new UserInfo();
            //获取值
            response_UserInfo.setId(userInfo.getId());
            response_UserInfo.setUsername(userInfo.getUsername());
            response_UserInfo.setPassword(userInfo.getPassword());
            response_UserInfo.setEmail(userInfo.getEmail());
            response_UserInfo.setPhone(userInfo.getPhone());
            response_UserInfo.setCreateTime(userInfo.getCreateTime());
            response_UserInfo.setUpdateTime(userInfo.getUpdateTime());
            return ServerResponse.createServerResponseBySuccess(null,response_UserInfo);
          
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }
    /**
     * 获取用户详细信息
     */
    @RequestMapping("/get_information.do")
    public ServerResponse get_information(HttpSession session){
        Object o=session.getAttribute(Const.CURRENTUSER);
        if (o != null && o instanceof UserInfo){
            UserInfo userInfo=(UserInfo)o;
           return ServerResponse.createServerResponseBySuccess(null,userInfo);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

    /**
     * 根据用户名获取密保问题
     */
    @RequestMapping("/forget_get_question.do")
    public ServerResponse forget_get_question(String username){
       return userService.forget_get_question(username);
    }

    /**
     * 提交问题答案接口
     */
    @RequestMapping("/forget_check_answer.do")
    public  ServerResponse forget_check_answer(String username,String question,String answer){
        return userService.forget_check_answer(username,question,answer);
    }

    /**
     * 忘记密码的重置密码
     */
    @RequestMapping("/forget_reset_password.do")
    public ServerResponse forget_reset_password(String username,String passwordNew,String forgetToken){
        return userService.forget_reset_password(username,passwordNew,forgetToken);
    }


    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout.do")
    public  ServerResponse logout(HttpSession session){
        session.removeAttribute(Const.CURRENTUSER);
        return  ServerResponse.createServerResponseBySuccess();
    }
    /**
     * 登录状态重置密码
     */
    @RequestMapping(value = "/reset_password.do")
    public  ServerResponse reset_password(HttpSession session,String passwordOld,String passwordNew){
        //校验用户是否登录
        Object o=session.getAttribute(Const.CURRENTUSER);
        if (o!=null && o instanceof UserInfo){
            UserInfo userInfo=(UserInfo)o;
            return userService.reset_password(userInfo,passwordOld,passwordNew);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());

    }

}


