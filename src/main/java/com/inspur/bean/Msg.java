package com.inspur.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回的类
 */
public class Msg {
    //状态码 100-失败 200-成功
    private int status;
    //提示信息
    private String msg;

    //用户要返回给浏览器的数据
    private Map<String, Object> data = new HashMap<String, Object>();

    public static Msg success() {
        Msg result = new Msg();
        result.setStatus(200);
        result.setMsg("处理成功！");
        return result;
    }

    public static Msg fail() {
        Msg result = new Msg();
        result.setStatus(100);
        result.setMsg("处理失败！");
        return result;
    }

    public Msg add(String key, Object value) {
        this.getData().put(key, value);
        return  this;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
