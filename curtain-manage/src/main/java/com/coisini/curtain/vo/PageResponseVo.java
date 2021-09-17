package com.coisini.curtain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页数据统一 view object
 *
 * @author pedro@TaleLin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponseVo<T> {

    private long total;

    private List<T> items;

    private long page;

    private long count;
}
