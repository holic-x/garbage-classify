package com.rc.framework.http;

import com.google.gson.Gson;

import java.util.Map;

/**
 * 自定义Res结果处理类（处理http请求的结果数据）
 */
public class ResUtil {

    /**
     * 校验响应结果是否正常
     */
    public static boolean validRes(String res){
        if(res!=null){
            Gson gson = new Gson();
            Map<String,Object> responseObj = gson.fromJson(res, Map.class);
            // 数据转化（0.0处理 转化后是double类型）
            if(String.valueOf(responseObj.get("code")).equals("0.0")){
                return true;
            }else{
                // 其他响应状态码处理，结合实际业务场景进行划分
                System.out.println("http数据响应异常");
                return false;
            }
        }
        // 响应结果为空，返回false
        return false;
    }


    /**
     * 获取data数据信息，根据指定参数集合
     */
    public static Map<String,Object> parseRes(String res){
        if(res!=null){
            Gson gson = new Gson();
            Map<String,Object> responseObj = gson.fromJson(res, Map.class);
            if(String.valueOf(responseObj.get("code")).equals("0.0")){
                return responseObj;
            }else{
                // 其他响应状态码处理，结合实际业务场景进行划分
                System.out.println("http数据响应异常");
                return null;
            }
        }
        // 响应结果为空，返回空的业务数据
       return null;
    }

}
