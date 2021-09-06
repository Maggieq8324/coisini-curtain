package com.coisini.curtain.core.common;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.data.domain.Page;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 分页拷贝对象
 * @author coisini
 * @date Aug 15, 2021
 * @Version 1.0
 */
public class PagingDozer<T, K> extends Paging {

    /**
     * 分页拷贝对象封装
     * @param pageData 分页对象
     * @param kClass 拷贝对象
     */
    @SuppressWarnings("unchecked")
    public PagingDozer(Page<T> pageData, Class<K> kClass) {
        this.initPageParameters(pageData);

        // 元数据
        List<T> lists = pageData.getContent();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        // 拷贝数据
        List<K> voList = new ArrayList<>();

        lists.forEach(item -> {
            K vo = mapper.map(item, kClass);
            voList.add(vo);
        });

        this.setData(voList);
    }

}
