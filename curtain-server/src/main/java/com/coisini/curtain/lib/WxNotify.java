package com.coisini.curtain.lib;

import com.coisini.curtain.exception.http.ServerErrorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Description 微信支付回调返回消息
 * @author coisini
 * @date Aug 23, 2021
 * @Version 1.0
 */
public class WxNotify {

    /**
     * 消息读取
     * @param stream
     * @return
     */
    public static String readNotify(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();

        String line;

        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new ServerErrorException(9999);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }

    /**
     * 读取失败
     * @return
     */
    public static String fail() {
        return "false";
    }

    /**
     * 读取成功
     * @return
     */
    public static String success() {
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }
}

