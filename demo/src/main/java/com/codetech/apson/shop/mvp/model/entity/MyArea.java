package com.codetech.apson.shop.mvp.model.entity;

public class MyArea {
    String name;
    String code;
    String flag;

    public String getFlag() {
        return flag;
    }

    public MyArea(String name, String code, String flag) {
        this.name = name;
        this.code = code;
        this.flag = flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
