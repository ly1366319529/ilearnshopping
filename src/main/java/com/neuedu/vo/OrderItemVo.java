package com.neuedu.vo;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItemVo  implements Serializable {
    private Long orderNo;
    private  Integer productId;
    private String productName;
    private String productImage;
    private BigDecimal currentUtinPrice;
    private Integer quantity;
    private  BigDecimal totalPrice;
    private String createTime;

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public BigDecimal getCurrentUtinPrice() {
        return currentUtinPrice;
    }

    public void setCurrentUtinPrice(BigDecimal currentUtinPrice) {
        this.currentUtinPrice = currentUtinPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
