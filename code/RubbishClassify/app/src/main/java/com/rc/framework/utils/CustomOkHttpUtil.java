package com.rc.framework.utils;

import com.google.gson.Gson;


import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 自定义OkHttp交互工具类
 */
public class CustomOkHttpUtil {

    private static String baseUrl = ConstUtil.HOST_SERVER_IP + "/rc-server/api/";

    /**
     * @param apiUrl
     * @return
     */
    public static String okHttpGet(String apiUrl) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();

            // url处理
            String requestUrl = baseUrl + apiUrl;
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .get()
                    .build();
            Response response = null;
            // 同步请求
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                System.out.println("响应结果：" + res);
                return res;
            } else {
                System.out.println("服务器连接失败");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过JSON数据方式交互数据(兼容性未处理)
     */
    /*
    private RcUser loginCheckByHttpAndJson() {
        try {
            JSONObject requestJson = new JSONObject();
            requestJson.put("userId", "1");
            String response = CustomOkHttpClientUtil.createHttpsPostByjson("http://192.168.2.135:8080/rc-server/api/getUserById", requestJson.toString(), "application/json");
            Gson gson = new Gson();
            RcUser loginUser = gson.fromJson(response, RcUser.class);
            return loginUser;
        } catch (Exception e) {
            System.out.println("服务处理失败");
            e.printStackTrace();
        }
        return null;
    }*/

    /**
     * okhttp post json方式
     *
     * @param apiUrl
     * @param requestJson
     * @return
     */
    public static String okHttpPostJson(String apiUrl, JSONObject requestJson) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();

            // url处理
            String requestUrl = baseUrl + apiUrl;
            System.out.println("访问rc-server【URL】：" + requestUrl);

            Gson gson = new Gson();
            // 使用Gson将对象转换为json字符串
            String json = gson.toJson(requestJson);
            // MediaType设置Content-Type 标头中包含的媒体类型值
            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .post(requestBody)
                    .build();
            Response response = null;
            // 同步请求
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                System.out.println("响应结果：" + res);
                return res;
            } else {
                System.out.println("服务器连接失败");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过okhttp3方式调用后台服务验证信息
     * 通过表单方式交互数据
     *
     * @return
     */
    private String okHttpPostForm(String apiUrl, Map<String, Object> requestMap) {
        try {
            // 设置请求参数
            FormBody.Builder formBody = new FormBody.Builder();
            for (String key : requestMap.keySet()) {
                formBody.add(key, String.valueOf(requestMap.get(key)));
            }

            // 创建连接
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(3000, TimeUnit.SECONDS)
                    .callTimeout(3000, TimeUnit.SECONDS)
                    .build();
            RequestBody requestBody = formBody.build();

            // url处理
            String requestUrl = baseUrl + apiUrl;

            // 设置请求的地址
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .post(requestBody).build();
            Response response = null;

            // 同步请求
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String res = response.body().string();
                System.out.println("响应结果：" + res);
                return res;
            } else {
                System.out.println("服务器连接失败");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
