package com.coisini.curtain.core.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 分页请求对象
 * @author coisini
 * @date Aug 15, 2021
 * @Version 1.0
 */
@Getter
@Setter
@Builder
public class PageCounter {

    /**
     * 页码
     */
    private Integer page;

    /**
     * 数量
     */
    private Integer count;

}
