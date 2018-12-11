package com.neuedu.service.impl;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.common.TokenCache;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.UserService;

import com.neuedu.utils.MD5Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service //表示是一个业务逻辑层的实现类
public class UserServiceImpl  implements UserService {

    // 从Spring容其中获取 userInfoMapper
   @Autowired
   UserInfoMapper userInfoMapper;


    @Override
    public ServerResponse register(UserInfo userInfo) {

        //1.参数非空校验
        if (userInfo==null){  //参数不为空                                                          //直接调用状态码
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
        //2.判断用户名是否已存在
        String username =userInfo.getUsername();
      // int username_count= userInfoMapper.checkUsername(username);
       /* if (username_count>0){
            return ServerResponse.createServerResponseByError(ResponseCode.EXISTS_USERNAME.getStatus(),ResponseCode.EXISTS_USERNAME.getMsg());
        }*/
        ServerResponse serverResponse=check_valid(username,Const.USERNAME);
        if (!serverResponse.isSuccess()){ //用户名已存在
            return ServerResponse.createServerResponseByError(ResponseCode.EXISTS_USERNAME.getStatus(),ResponseCode.EXISTS_USERNAME.getMsg());
        }

        //3.判断邮箱是否已存在
        String email=userInfo.getEmail();
        //int email_result=userInfoMapper.checkEmail(email);
        /*if (email_result>0){//邮箱已存在
            return ServerResponse.createServerResponseByError(ResponseCode.EXISTS_EMAIL.getStatus(),ResponseCode.EXISTS_EMAIL.getMsg());
        }*/
        ServerResponse email_serverResponse=check_valid(email,Const.EMAIL);
        if (!email_serverResponse.isSuccess()){ //邮箱已存在
            return ServerResponse.createServerResponseByError(ResponseCode.EXISTS_EMAIL.getStatus(),ResponseCode.EXISTS_EMAIL.getMsg());
        }

        //4.注册
        userInfo.setRole(Const.USER_ROLE_CUSTOMER);
        userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
         int userInfo_result=userInfoMapper.insert(userInfo);
        //5.返回结果
        if (userInfo_result>0){
            return ServerResponse.createServerResponseBySuccess("注册成功");
        }
        return  ServerResponse.createServerResponseByError("注册失败");
    }

    @Override
    public ServerResponse login(String username, String password) {
        //1.参数非空校验
        if (StringUtils.isBlank(username)){//用户名不能为空
            return ServerResponse.createServerResponseByError("用户名不能为空");
        }
        if (StringUtils.isBlank(password)){//密码不能为空
            return  ServerResponse.createServerResponseByError("密码不能为空");
        }
        //2.检查username是否存在
       /* int username_result=userInfoMapper.checkUsername(username);
        if (username_result<=0){//用户名不存在
            return ServerResponse.createServerResponseByError("用户名不存在");
        }*/
       ServerResponse serverResponse=check_valid(username,Const.USERNAME);
       if (serverResponse.isSuccess()){ //用户名不存在
           return ServerResponse.createServerResponseByError(ResponseCode.NOT_EXISTS_USERNAME.getStatus(),ResponseCode.NOT_EXISTS_USERNAME.getMsg());
       }
        //3.根据用户名和密码查询
        UserInfo userInfo=userInfoMapper.selectByUsernamePassword(username,MD5Utils.getMD5Code(password));
        if (userInfo==null){//密码错误
            return ServerResponse.createServerResponseByError("密码错误");
        }
        //4.处理结果并返回
        userInfo.setPassword("");
        return ServerResponse.createServerResponseBySuccess(null,userInfo);
    }

    /**
     * 检查用户名或邮箱是否有效
     */
    @Override
    public ServerResponse check_valid(String str, String type) {
        //1.参数是否为空
        if (StringUtils.isBlank(str)||StringUtils.isBlank(type)){
            return ServerResponse.createServerResponseByError("用户名不能为空");
        }
        //2.判断用户名或邮箱是否以存在
        if (type.equals(Const.USERNAME)) {
            int username_result=userInfoMapper.checkUsername(str);
            if (username_result>0){
                return ServerResponse.createServerResponseByError(ResponseCode.EXISTS_USERNAME.getStatus(),ResponseCode.EXISTS_USERNAME.getMsg());
            }
            return  ServerResponse.createServerResponseBySuccess("成功");
        }else if (type.equals(Const.EMAIL)){
            int email_result=userInfoMapper.checkUsername(str);
            if (email_result>0){
                return ServerResponse.createServerResponseByError(ResponseCode.EXISTS_EMAIL.getStatus(),ResponseCode.EXISTS_EMAIL.getMsg());
            }
            return  ServerResponse.createServerResponseBySuccess("成功");
        }
        //3.返回结果

        return ServerResponse.createServerResponseByError("type参数传递成功");
    }

    /**
     * 根据用户名获取密保问题
     * @param username
     * @return
     */
    @Override
    public ServerResponse forget_get_question(String username) {

        //1.参数的非空校验
        if(StringUtils.isBlank(username)){//用户名不能为空
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
        //2.判断用户是否存在
        ServerResponse serverResponse=check_valid(username,Const.USERNAME);
        if (serverResponse.getStatus()!=ResponseCode.EXISTS_USERNAME.getStatus()){//用户名不存在
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_EXISTS_USERNAME.getStatus(),ResponseCode.NOT_EXISTS_USERNAME.getMsg());
        }
        //3.查询密保问题
        String question=userInfoMapper.selectQuestionByUsername(username);
        if (StringUtils.isBlank(question)){
            return ServerResponse.createServerResponseByError("问题不能为空");
        }
        //4.查寻返回结果

        return ServerResponse.createServerResponseBySuccess(null,question);
    }

    @Override
    public ServerResponse forget_check_answer(String username, String question, String answer) {
        //1.参数为空校验
        if (StringUtils.isBlank(username)||StringUtils.isBlank(question)||StringUtils.isBlank(answer)){
            return ServerResponse.createServerResponseByError("参数不能为空");
        }

        //2.校验答案
        //从sql语句：
        int count=userInfoMapper.checkanswerByUsernameAndQuestion(username,question,answer);
        if (count<=0){
            return ServerResponse.createServerResponseByError("答案错误");
        }

        //返回用户的唯一标识  --> username 是用户的唯一标识   ---> token   往服务端
        String uuid=UUID.randomUUID().toString();
        TokenCache.put(username,uuid);   //是唯一的字符串，随机的

        //3.返回结果
        return ServerResponse.createServerResponseBySuccess(null,uuid);


    }

    /**
     * 提交问题答案
     *
     * @param username
     * @param passwordNew
     * @return
     */

    @Override
    public ServerResponse forget_reset_password(String username, String passwordNew,String forgetToken) {
        //1.参数类型
        if (StringUtils.isBlank(username)||StringUtils.isBlank(passwordNew)||StringUtils.isBlank(forgetToken)){
            return ServerResponse.createServerResponseByError("参数不能为空");
        }

        //叫验ToKe
        String token=TokenCache.get(username);
        if (StringUtils.isBlank(token)){
            return ServerResponse.createServerResponseByError("token 不存在");
        }
        if (!token.equals(forgetToken)) {
            return ServerResponse.createServerResponseByError("token不一致");
        }

        //2.更新密码

        int count=userInfoMapper.updatePasswordByUsername(username,MD5Utils.getMD5Code(passwordNew));
        if (count<=0){
            return ServerResponse.createServerResponseByError("密码修改失败");
        }


        //3.返回结果
        return ServerResponse.createServerResponseBySuccess();
    }

    @Override
    public ServerResponse reset_password(UserInfo userInfo, String passwordOld, String passwordNew) {

        //1.参数的非空校验

        if ( StringUtils.isBlank(passwordOld)||StringUtils.isBlank(passwordNew)){
            return  ServerResponse.createServerResponseByError("参数不能为空");
        }
        // 2.校验旧密码
        UserInfo userInfoOld=userInfoMapper.selectByUsernamePassword(userInfo.getUsername(),MD5Utils.getMD5Code(passwordOld));
        if (userInfoOld==null){
            return  ServerResponse.createServerResponseByError("旧密码错误");
        }
        // 3.修改密码
        int count=userInfoMapper.updatePasswordByUsername(userInfo.getUsername(),MD5Utils.getMD5Code(passwordNew));
        //4. 返回结果
        if (count<=0){
            return ServerResponse.createServerResponseByError("密码修改失败");
        }
        return ServerResponse.createServerResponseBySuccess("密码修改成功");
    }




}
