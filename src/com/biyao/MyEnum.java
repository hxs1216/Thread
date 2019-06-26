package com.biyao;

import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * @Author:hxs
 * @Description:枚举类
 */
public enum MyEnum {
    ONE(1,"红"),
    TWO(2,"橙"),
    THREE(3,"黄"),
    FOUR(4,"绿");
    private Integer code;
    private String message;

    MyEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static MyEnum forEachCount(int index){
        MyEnum[] values = MyEnum.values();
        for(MyEnum element:values){
            if(element.getCode() == index){
                return element;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
