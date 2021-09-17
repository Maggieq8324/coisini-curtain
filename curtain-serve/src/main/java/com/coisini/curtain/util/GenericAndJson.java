package com.coisini.curtain.util;

import com.coisini.curtain.exception.http.ServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @Description 通用JSON序列化
 * @author coisini
 * @date Aug 16, 2021
 * @Version 1.0
 */
@Component
public class GenericAndJson {

    private static ObjectMapper mapper;

    /**
     * 注入mapper
     * @param mapper
     */
    @Autowired
    public void setMapper(ObjectMapper mapper) {
        GenericAndJson.mapper = mapper;
    }

    /**
     * Object转JSON
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String objectToJson(T t) {
        try {
            return GenericAndJson.mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }

    /**
     * JSON转Object
     * @param str
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String str, TypeReference<T> tr) {

        if (StringUtils.isEmpty(str)) {
            return null;
        }

        try {
            return GenericAndJson.mapper.readValue(str, tr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }

    }

    /**
     * JSON转List
     * @param str
     * @param <T>
     * @return
     */
    public static <T> List<T> jsontToList(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }

        try {
            List<T> list = GenericAndJson.mapper.readValue(str, new TypeReference<List<T>>() {
            });
            return list;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }

    }

    /**
     * JSON转List
     * @param str
     * @param tTypeReference
     * @param <T>
     * @return
     */
    public static <T> T jsonToList(String str, TypeReference<T> tr) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }

        try {
            T t = GenericAndJson.mapper.readValue(str, tr);
            return t;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }

}
