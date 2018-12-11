package com.neuedu.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 服务端返回给响应前端的高复用对象 ServerResponse
 * 封装返回前端的高复用对象
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) //传递数据的时候非空字段不会转化
public class ServerResponse<T> {

    private  int status;  //状态码
    private T data;    // 返回前端的接口数据
    private String msg;//接口提示信息


    private ServerResponse() {
    }

    public ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    /**
     * 判断接口是否调用成功
     * @return
     */

    @JsonIgnore  //在json数据转ServerResponse字段时将 isSuccess字段忽略掉
    public  boolean isSuccess(){
      return this.status==Const.SUCCESS_CODE;
    }

    /**
     * 接口调用成功的情况下
     * */

    public static ServerResponse createServerResponseBySuccess(){
        return  new ServerResponse(Const.SUCCESS_CODE);
    }
    public static ServerResponse createServerResponseBySuccess(String msg){
        //返回
        return  new ServerResponse(Const.SUCCESS_CODE,msg);
    }
    public static<T> ServerResponse createServerResponseBySuccess(T data){
        //返回
        return  new ServerResponse(Const.SUCCESS_CODE,data);
    }

    public static<T> ServerResponse createServerResponseBySuccess(String msg,T data){
        //返回
        return  new ServerResponse(Const.SUCCESS_CODE,data,msg);
    }

    /**
     * 调用失败的情况下
     * @return
     */
    public static ServerResponse createServerResponseByError(){
        //返回失败的状态码
        return  new ServerResponse(Const.SUCCESS_ERROR);
    }
    public static ServerResponse createServerResponseByError(String msg){
        //返回失败的状态码
        return  new ServerResponse(Const.SUCCESS_ERROR,msg);
    }
    public static ServerResponse createServerResponseByError(int status){
        //返回失败的状态码
        return  new ServerResponse(status);
    }
    public static ServerResponse createServerResponseByError(int status,String msg){
        //返回失败的状态码
        return  new ServerResponse(status,msg);
    }
}
