package com.library.base.entity.basemodel;


import com.library.base.global.GlobalConstant;

/**
 * Created by Sunflower on 2016/1/11.
 */
public class ResponseModel<T> {

    public String code;
    public String message;
    public T result;


    public boolean isSuccess() {
        return code.equals( GlobalConstant.REQUEST_SUCCESS);
    }
//    public String resultCode;
//    public String resultMessage;
//    public T resultData;
//
//
//    public boolean isSuccess() {
//        return resultCode.equals(GlobalConstant.REQUEST_SUCCESS);
//    }


    public String getRet() {
        return code;
    }

    public void setRet(String ret) {
        this.code = ret;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public T getResult() {
        return result;
    }

    public ResponseModel setResult(T result) {
        this.result = result;
        return this;
    }
}
