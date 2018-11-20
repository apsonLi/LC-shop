package com.codetech.apson.shop.mvp.model.entity;

import java.util.List;

public class MyBaseResponse<T> {

    /**
     * status : 200
     * msg : OK
     * res : [{"true_name":"unamed-10010","tel":"","birthday":"","referral_id":"3,1"}]
     */

    private String status;
    private String msg;
    private List<T> res;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getRes() {
        return res;
    }

    public void setRes(List<T> res) {
        this.res = res;
    }
}
