package com.neuedu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ShippingMapper;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.ServerSocket;
import java.util.List;
import java.util.Map;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    ShippingMapper shippingMapper;

    /**
     * 地址添加
     */
    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        //参数校验
        if (shipping==null){
            return ServerResponse.createServerResponseByError("参数不能为空");
        }
        //添加地址
        shipping.setUserId(userId);
        shippingMapper.insert(shipping);

        //返回结果
        Map<String,Integer> map= Maps.newHashMap();
        map.put("shippingId",shipping.getId());
        return ServerResponse.createServerResponseBySuccess(map);
    }

    /**
     * 地址删除  根据userId和地址id
     * @param userId
     * @param shippingId 地址id
     * @return
     */
    @Override
    public ServerResponse delete(Integer userId, Integer shippingId) {
        //参数的非空校验
        if (shippingId==null){
            return ServerResponse.createServerResponseByError("地址不能为空");
        }
        //删除
        int count=shippingMapper.deleteByUserIdAndShipping(shippingId,userId);
        if (count>0){
            return ServerResponse.createServerResponseBySuccess();
        }
        return ServerResponse.createServerResponseByError("删除失败");
    }

    @Override
    public ServerResponse update(Shipping shipping) {
        //参数不为空
        if (shipping==null){
            return ServerResponse.createServerResponseByError("地址不能为空");
        }
        //修改
        int count=shippingMapper.updateByUserIdAndShipping(shipping);
        if (count>0){
            return ServerResponse.createServerResponseBySuccess();
        }

        return ServerResponse.createServerResponseByError("修改失败");
    }

    @Override
    public ServerResponse list(Integer shippingId) {
        //参数的非空校验
        if (shippingId==null){
            return ServerResponse.createServerResponseByError("地址不能为空");
        }
        Shipping shipping=shippingMapper.selectByPrimaryKey(shippingId);
        return ServerResponse.createServerResponseBySuccess(shipping);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse list_select(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingsList=shippingMapper.selectAll();
        PageInfo pageInfo=new PageInfo(shippingsList);
        return ServerResponse.createServerResponseBySuccess(pageInfo);
    }


}
