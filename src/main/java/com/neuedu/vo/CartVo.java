package com.neuedu.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 返回前端的购物车实体类
 */
public class CartVo {
    //购物车信息集合
    private List<CartProductVo> cartProductVoList;
    //是否全选
    private boolean ischeched;
    //总价
    private BigDecimal carttoalprice;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public boolean isIscheched() {
        return ischeched;
    }

    public void setIscheched(boolean ischeched) {
        this.ischeched = ischeched;
    }

    public BigDecimal getCarttoalprice() {
        return carttoalprice;
    }

    public void setCarttoalprice(BigDecimal carttoalprice) {
        this.carttoalprice = carttoalprice;
    }
}
