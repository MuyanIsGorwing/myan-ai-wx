package com.myan.myanai.common;

import lombok.Data;

@Data
public class R {

    private int code;
    private String msg;
    private Object data;

    public R ok(){
        this.code = 200;
        this.msg = "success";
        return this;
    }

    public R ok(Object data){
        this.code = 200;
        this.msg = "success";
        this.data = data;
        return this;
    }

    public R ok(String msg){
        this.code = 200;
        this.msg = msg;
        return this;
    }

    public R fail(String msg){
        this.code = 500;
        this.msg = msg;
        return this;
    }

    public R fail(int code, String msg){
        this.code = code;
        this.msg = msg;
        return this;
    }

}
