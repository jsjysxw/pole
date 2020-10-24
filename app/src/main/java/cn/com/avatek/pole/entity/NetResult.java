package cn.com.avatek.pole.entity;


import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by shenxw on 2017/2/6.
 */

public class NetResult<T> {
    private int state;
    private String msg;
    private DataResult<T> data;

    public int getState() {
        return state;
    }

    public void setState(int success) {
        this.state = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataResult<T> getData() {
        return data;
    }

    public void setData(DataResult<T> data) {
        this.data = data;
    }

    public static NetResult fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(NetResult.class, clazz);
        NetResult result = null;
        try {
            result = gson.fromJson(json, objectType);
        }catch (Exception e){
            result = new NetResult();
        }
        return result;
    }
    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
