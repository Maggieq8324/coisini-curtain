package com.coisini.curtain.util;

import com.coisini.curtain.exception.http.ServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.AttributeConverter;
import java.util.List;

/**
 * @Description List与JSON 映射转换器
 * @author coisini
 * @date Aug 16, 2021
 * @Version 1.0
 */
public class ListAndJson implements AttributeConverter<List<Object>, String> {

    @Autowired
    private ObjectMapper mapper;

    /**
     * 转换成数据库字段
     * @param objects
     * @return
     */
    @Override
    public String convertToDatabaseColumn(List<Object> objects) {
        try {
            return mapper.writeValueAsString(objects);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }

    /**
     * 转换成实体字段
     * @param s
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Object> convertToEntityAttribute(String s) {
        try {
            return mapper.readValue(s, List.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }
}
