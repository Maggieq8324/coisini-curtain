package com.coisini.curtain.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.vo.PageResponseVo;

import java.util.List;

/**
 * @author colorful@TaleLin
 */
public class PageUtil {

    public static <T> PageResponseVo<T> build(IPage<T> iPage) {
        return new PageResponseVo<>(iPage.getTotal(), iPage.getRecords(), iPage.getCurrent(), iPage.getSize());
    }

    public static <K, T> PageResponseVo<K> build(IPage<T> iPage, List<K> records) {
        return new PageResponseVo<>(iPage.getTotal(), records, iPage.getCurrent(), iPage.getSize());
    }

}
