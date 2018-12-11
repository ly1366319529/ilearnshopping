package com.neuedu.controller.manage;


import com.neuedu.common.Const;
import com.neuedu.common.ResponseCodeCategory;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/category/")
public class categorymanagecontroller {

    @Autowired
    CategoryService categoryService;
    /**
     * 获得类别的子节点（平级）
     */
    @RequestMapping("add_category.do")
    public ServerResponse add_category(HttpSession session,@RequestParam(required = false,defaultValue = "0") Integer parentId,String categoryName) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByError(ResponseCodeCategory.NEED_LOGIN.getStatus(), ResponseCodeCategory.NEED_LOGIN.getMsg());
        }
        //判断用户权限
        if (userInfo.getRole() != Const.USER_ROLE_ADIMN) {
            return ServerResponse.createServerResponseByError(ResponseCodeCategory.NO_PRIVILEGE.getStatus(), ResponseCodeCategory.NO_PRIVILEGE.getMsg());
        }
        return categoryService.add_category(parentId,categoryName);
    }
    /**
     * 增加节点
     */
    @RequestMapping("get_category.do")
    public ServerResponse get_category(HttpSession session, Integer categoryId) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByError(ResponseCodeCategory.NEED_LOGIN.getStatus(), ResponseCodeCategory.NEED_LOGIN.getMsg());
        }
        //判断用户权限
        if (userInfo.getRole() != Const.USER_ROLE_ADIMN) {
            return ServerResponse.createServerResponseByError(ResponseCodeCategory.NO_PRIVILEGE.getStatus(), ResponseCodeCategory.NO_PRIVILEGE.getMsg());
        }
        return categoryService.get_category(categoryId);
    }
    /**
     * 增加节点
     */
    @RequestMapping("update_category.do")
    public ServerResponse update_category(HttpSession session, Integer categoryId,String categoryName) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByError(ResponseCodeCategory.NEED_LOGIN.getStatus(), ResponseCodeCategory.NEED_LOGIN.getMsg());
        }
        //判断用户权限
        if (userInfo.getRole() != Const.USER_ROLE_ADIMN) {
            return ServerResponse.createServerResponseByError(ResponseCodeCategory.NO_PRIVILEGE.getStatus(), ResponseCodeCategory.NO_PRIVILEGE.getMsg());
        }
        return categoryService.update_category(categoryId,categoryName);
    }


    /**
     * 递归查询子节点的id
     */
    @RequestMapping("get_deep_category.do")
    public ServerResponse get_deep_category(HttpSession session, Integer categoryId,String categoryName) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByError(ResponseCodeCategory.NEED_LOGIN.getStatus(), ResponseCodeCategory.NEED_LOGIN.getMsg());
        }
        //判断用户权限
        if (userInfo.getRole() != Const.USER_ROLE_ADIMN) {
            return ServerResponse.createServerResponseByError(ResponseCodeCategory.NO_PRIVILEGE.getStatus(), ResponseCodeCategory.NO_PRIVILEGE.getMsg());
        }
        return categoryService.get_deep_category(categoryId);
    }
}
