package com.asahi.demo.springSecurity.utils;

import java.util.Map;

public class MessageUtils {

    private static final Integer SUCCESS_CODE = 1;
    private static final Integer ERROR_CODE = 0;
    private static final String SUCCESS_STATE = "SUCCESS";
    private static final String ERROR_STATE = "ERROR";

    private static Integer code;
    private static String msg;
    private static String returnState;
    private static Map<String,Object> voMap;

    private MessageUtils() {
    }
    private MessageUtils(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public static Integer getCode() {
        return code;
    }

    public static void setCode(Integer code) {
        MessageUtils.code = code;
    }

    public static String getMsg() {
        return msg;
    }

    public static void setMsg(String msg) {
        MessageUtils.msg = msg;
    }

    public static String getReturnState() {
        return returnState;
    }

    public static void setReturnState(String returnState) {
        MessageUtils.returnState = returnState;
    }

    public static Map<String, Object> getVoMap() {
        return voMap;
    }

    public static void setVoMap(Map<String, Object> voMap) {
        MessageUtils.voMap = voMap;
    }

    public static void successs(String msg,Map voMap){
        MessageUtils.code = MessageUtils.SUCCESS_CODE;
        MessageUtils.msg = msg;
        MessageUtils.returnState = MessageUtils.SUCCESS_STATE;
        MessageUtils.voMap = voMap;
    }

    public static void error(String msg,Map voMap){
        MessageUtils.code = MessageUtils.ERROR_CODE;
        MessageUtils.msg = msg;
        MessageUtils.returnState = MessageUtils.ERROR_STATE;
        MessageUtils.voMap = voMap;
    }
    public static void error(String msg){
        MessageUtils.code = MessageUtils.ERROR_CODE;
        MessageUtils.msg = msg;
        MessageUtils.returnState = MessageUtils.ERROR_STATE;
    }

    @Override
    public String toString() {
        return "MessageUtils{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", returnState='" + returnState + '\'' +
                ", voMap=" + voMap +
                '}';
    }
}
