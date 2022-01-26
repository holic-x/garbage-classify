package com.rc.framework.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NetworkUtil {

    /**
     * 测试ip访问是否正常
     *
     * @throws Exception
     */

    public static boolean testConnIp(String address) throws Exception {
        Process process = Runtime.getRuntime().exec("ping " + address);
        InputStreamReader r = new InputStreamReader(process.getInputStream());
        LineNumberReader returnData = new LineNumberReader(r);
        String returnMsg = "";
        String line = "";
        while ((line = returnData.readLine()) != null) {
            System.out.println(line);
            returnMsg += line;
        }
        // 校验返回的结果数据
        if (returnMsg.indexOf("100% loss") != -1) {
            System.out.println("与 " + address + " 连接不畅通.");
            return false;
        } else {
            System.out.println("与 " + address + " 连接畅通.");
            return true;
        }
    }

    /**
     * wifi模式下访问ip和指定端口成功
     */
    public static boolean testConnIpAndPort(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
            System.out.println("指定IP：[" + host + "] 访问端口号：[" + port + "]连接成功");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
