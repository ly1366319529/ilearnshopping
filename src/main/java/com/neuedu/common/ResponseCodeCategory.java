package com.neuedu.common;

public enum ResponseCodeCategory {

    NEED_LOGIN(2,"需要登录"),
    NO_PRIVILEGE(3,"无权限登录"),
    //商品状态
    PRODUCT_ONLINE(1,"在售状态"),
    PRODUCT_OFFLINE(2,"下架"),
    PRODUCT_DELETE(3,"删除"),

    PRODUCT_CHECKEED(1,"已勾选"),
    PRODUCT_UNCHECKEED(0,"未勾选"),


     ORDER_CANCELED(0,"已取消"),
    ORDER_UN_PAY(10,"未付款"),
    ORDER_PAYED(20,"已付款"),
    ORDER_SEND(30,"已发货"),
    ORDER_SUCCESS(40,"交易成功"),
    ORDER_CLOSED(50,"交易失败"),


    ONLINE(1,"线上支付"),

    AlIPAY(1,"支付宝支付")
    ;
    private int status;
    private  String msg;

    ResponseCodeCategory(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



    public static  ResponseCodeCategory orderpaystatus(Integer status){
        for (ResponseCodeCategory responseCodeCategory : values()) {
            if (status==responseCodeCategory.getStatus()){
                return responseCodeCategory;
            }
        }
        return null;
    }
   /* public static  ResponseCodeCategory paystatus(Integer status){
        for (ResponseCodeCategory responseCodeCategory : values()) {
            if (status==responseCodeCategory.getStatus()){
                return responseCodeCategory;
            }
        }
        return null;
    }*/
}
