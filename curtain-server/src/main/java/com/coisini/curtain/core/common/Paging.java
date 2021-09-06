package com.coisini.curtain.core.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.util.List;

/**
 * @Description 分页返回对象
 * @author coisini
 * @date Aug 15, 2021
 * @Version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class Paging<T> {

    /**
     * 总数量
     */
    private Long total;

    /**
     * 当前请求数量
     */
    private Integer count;

    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 总页码
     */
    private Integer totalPage;

    /**
     * 数据
     */
    private List<T> data;

    /**
     * 构造函数
     */
    public Paging(Page<T> pageData) {
        this.initPageParameters(pageData);
    }

    /**
     * 构造分页参数
     * @param pageData
     */
    void initPageParameters(Page<T> pageData) {
        this.total = pageData.getTotalElements();
        this.count = pageData.getSize();
        this.page = pageData.getNumber();
        this.totalPage = pageData.getTotalPages();
        this.data = pageData.getContent();
    }

}
