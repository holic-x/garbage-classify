package com.rc.framework.utils;

public class ConstUtil {

    // 宿主机服务IP
    public static String HOST_IP = "192.168.2.135";

    // 腾讯云服务IP
    public static String TX_CLOUD_IP = "81.71.48.35";

    // mysql 访问端口
    public static int MYSQL_PORT = 3306;

    // mysql DB访问配置（driver、url、user、pwd）
    public static String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static String DB_TX_CLOUD_URL = "jdbc:mysql://" + TX_CLOUD_IP + ":" + MYSQL_PORT + "/rubbish_classify";
    public static String DB_HOST_URL = "jdbc:mysql://" + HOST_IP + ":" + MYSQL_PORT + "/rubbish_classify";
    public static String DB_USER = "rc_user";
    public static String DB_PWD = "rc_user";

    // server接口访问url
    public static String HOST_SERVER_IP = "http://" + ConstUtil.HOST_IP + ":8080" ;
    public static String TX_CLOUD_SERVER_IP = "http://" + ConstUtil.TX_CLOUD_IP + ":8080" ;



}
