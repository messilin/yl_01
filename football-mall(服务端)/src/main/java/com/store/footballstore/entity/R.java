package com.store.footballstore.entity;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String,Object> {
//    serialVersionUID作用是序列化时保持版本的兼容性，即在版本升级时反序列化仍保持对象的唯一性。
//    final表示声明常量
    private static final long serialVersionUID = 1L;

    //构造函数
    public R() {
        put("code",0);
    }

    public static R error(){
        return error(500,"未知异常，请联系管理员");
    }
    public static R error(String msg){
        return error(500,msg);
    }
    public static R error(int code,String msg){
        R r=new R();
        r.put("code",code);
        r.put("msg",msg);
        return r;
    }
    public static R ok(String msg){
        R r=new R();
        r.put("msg",msg);
        return r;
    }
    public static R ok(Map<String,Object> map){
        R r=new R();
        r.putAll(map);
        return r;
    }
//    调用了构造函数，返回的map是("code",0)
    public static R ok(){
        return new R();
    }
    public R put(String key,Object value){
        super.put(key,value);
        return this;
    }
}
