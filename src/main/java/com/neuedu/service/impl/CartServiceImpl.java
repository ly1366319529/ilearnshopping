package com.neuedu.service.impl;

import com.google.common.collect.Lists;
import com.neuedu.common.ResponseCodeCategory;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CartMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.CartService;
import com.neuedu.service.ProductService;
import com.neuedu.service.UserService;
import com.neuedu.utils.BigDecimalUtils;
import com.neuedu.vo.CartProductVo;
import com.neuedu.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {


    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    /**
     * 购物车添加商品
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    @Override
    public ServerResponse add(Integer userId,Integer productId, Integer count) {
        //参数的非空校验
        if (productId==null||count==null){
            return ServerResponse.createServerResponseByError("参数不能为空");
        }
        Product product=productMapper.selectByPrimaryKey(productId);
        if (product==null){
            return ServerResponse.createServerResponseByError("要添加的商品不存在");
        }
        // 根据product和userId查询购物信息
        Cart cart=cartMapper.selectCartByUserIdAndProductId(userId,productId);
        if (cart==null){
            //添加
            Cart cart1=new Cart();
            cart1.setUserId(userId);
            cart1.setProductId(productId);
            cart1.setQuantity(count);
            cart1.setChecked(ResponseCodeCategory.PRODUCT_CHECKEED.getStatus());
            cartMapper.insert(cart1);
        }else {
            Cart cart1=new Cart();
            cart1.setId(cart.getId());
            cart1.setProductId(productId);
            cart1.setUserId(userId);
            cart1.setQuantity(cart.getQuantity()+count);
            cart1.setChecked(cart.getChecked());
            cartMapper.updateByPrimaryKey(cart1);
        }
        CartVo cartVo=getCartVoLimit(userId);
        return ServerResponse.createServerResponseBySuccess(cartVo);
    }

    /**
     * 购物车列表
     * @param userId
     * @return
     */
    @Override
    public ServerResponse list(Integer userId) {
        CartVo cartVo=getCartVoLimit(userId);
        return ServerResponse.createServerResponseBySuccess(cartVo);
    }

    /**
     * 更新购物车商品数量
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    @Override
    public ServerResponse update(Integer userId,Integer productId, Integer count) {
        //参数非空判断
        if (productId==null||count==null){
            return ServerResponse.createServerResponseByError("参数不能为空");
        }
        //查询购物车中的商品
        Cart cart=cartMapper.selectCartByUserIdAndProductId(userId,productId);
        if (cart!=null){
            //更新数量
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);
        }

        //返回cartvo
        return ServerResponse.createServerResponseBySuccess(getCartVoLimit(userId));
    }

    /**
     * 删除购物车中的某个商品
     * @param userId
     * @param productIds
     * @return
     */
    @Override
    public ServerResponse delete_product(Integer userId, String productIds) {
        //参数非空校验
        if (productIds==null||productIds.equals("")){
            return  ServerResponse.createServerResponseByError("参数不能为空");
        }
        //produuctIds-->List<Integer>
        List<Integer> productIdsList=Lists.newArrayList();
        String[] productIdsArr= productIds.split(",");
        if (productIdsArr!=null&&productIdsArr.length>0){
            for (String productIdstr: productIdsArr) {
                Integer productId=Integer.parseInt(productIdstr);
                productIdsList.add(productId);
            }
        }
        //调用dao
        cartMapper.deleteByUseriadAndProductIds(userId,productIdsList);
        //返回结果
        return ServerResponse.createServerResponseBySuccess(getCartVoLimit(userId));
    }

    /**
     * 购物车选中某一商品
     * @param userId
     * @param productId
     * @return
     */
    @Override
    public ServerResponse select_product(Integer userId, Integer productId,Integer check) {
        //非空校验
       /* if (productId==null||productId.equals("")){
            return  ServerResponse.createServerResponseByError("参数不能为空");
        }*/
        //调用dao层取消选中
        cartMapper.selectOrUnselectProduct(userId,productId,check);
        //返回结果

        return ServerResponse.createServerResponseBySuccess(getCartVoLimit(userId));
    }

    @Override
    public ServerResponse get_cart_product_count(Integer userId) {

       int quantity= cartMapper.get_cart_product_count(userId);
        return ServerResponse.createServerResponseBySuccess(quantity);
    }

    /**
     * 获取cartVo
     */
    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo=new CartVo();
        //:根据userId查询购物信息--》List<Cart>
       List<Cart> cartList=cartMapper.selectCartByUserId(userId);
        //2.List<Cart> -->List<CartProductVo>
        List<CartProductVo> cartProductVoList=Lists.newArrayList();
        //购物车总价格
        BigDecimal carttotalprice=new BigDecimal("0");
        if (cartList!=null&&cartList.size()>0){
            for (Cart cart : cartList) {
                CartProductVo cartProductVo=new CartProductVo();
                cartProductVo.setId(cart.getId());
                cartProductVo.setQuantity(cart.getQuantity());
                cartProductVo.setUserId(cart.getUserId());
                cartProductVo.setChecked(cart.getChecked());
                //查询商品
                Product product=productMapper.selectByPrimaryKey(cart.getProductId());
                if (product != null){
                    cartProductVo.setProductId(product.getId());
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductStock(product.getStock());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    int stock=product.getStock();
                    int limitProductCount=0;
                    if (stock>=cart.getQuantity()){//商品库存充足
                        limitProductCount=cart.getQuantity();
                        cartProductVo.setLimitQuantity("LIMIT_NUM_SUCCESS");
                    }else {//商品库存充足
                        limitProductCount=stock;
                        //更新购物车中的数量
                        Cart cart1=new Cart();
                        cart1.setId(cart.getId());
                        cart1.setQuantity(stock);
                        cart1.setProductId(cart.getProductId());
                        cart1.setChecked(cart.getChecked());
                        cart1.setUserId(cart.getUserId());
                        cartMapper.updateByPrimaryKey(cart1);
                        cartProductVo.setLimitQuantity("LIMIT_NUM_FATL");
                    }
                    //可购买数量
                    cartProductVo.setQuantity(limitProductCount);

                    cartProductVo.setProductTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(),Double.valueOf(cartProductVo.getQuantity())));


                }/*else{
                    return  ServerResponse.createServerResponseByError("商品为空");
                }*/
                //计算总价
                if (cartProductVo.getChecked()==ResponseCodeCategory.PRODUCT_CHECKEED.getStatus()) {
                    carttotalprice = BigDecimalUtils.add(carttotalprice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }

        cartVo.setCartProductVoList(cartProductVoList);
        //3.计算总价格
        cartVo.setCarttoalprice(carttotalprice);

        //4.判断购物车是否全选
        int count=cartMapper.isCheckedAll(userId);
        if (count>0){
            cartVo.setIscheched(false);
        }else {
            cartVo.setIscheched(true);
        }
        //5.返回结果
        return cartVo;
            }
}
