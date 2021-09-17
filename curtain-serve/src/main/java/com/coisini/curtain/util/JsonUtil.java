package com.coisini.curtain.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @Description Json
 * @author coisini
 * @date Jul 21, 2021
 * @Version 1.0
 */
public class JsonUtil {

    /**
     * 读取JSON文件转换为字符串
     * @param filePath
     * @return
     */
    public static String readJsonFile(String filePath) {
        String jsonStr = "";
        try {
            File jsonFile = new File(filePath);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
