package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;

import javax.servlet.http.HttpSession;

public interface UserService {//特性：方法的定义

    /**
     * 注册接口
     * @param userInfo
     * @return
     */
    public ServerResponse register(UserInfo userInfo);

    /**
     * 登录接口
     */
    public  ServerResponse login(String username,String password);
    /**
     * 检查用户名或邮箱是否有效
     */
    public  ServerResponse check_valid(String str,String type);

    /**
     * 根据用户名获取密保问题
     * @param username
     * @return
     */

    public ServerResponse forget_get_question(String username);
    /**
     * 提交问题答案接口
     */
    public  ServerResponse forget_check_answer(String username,String question,String answer);

    /**
     * 忘记密码的重置密码
     */
    public ServerResponse forget_reset_password(String username,String passwordNew,String forgetToken);

    /**
     * 登录状态下修改密码
     * @param
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    ServerResponse reset_password(UserInfo userInfo, String passwordOld, String passwordNew);
}
