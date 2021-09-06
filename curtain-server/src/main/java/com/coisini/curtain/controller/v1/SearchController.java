package com.coisini.curtain.controller.v1;

import com.coisini.curtain.core.common.PageCounter;
import com.coisini.curtain.core.common.PagingDozer;
import com.coisini.curtain.entity.Spu;
import com.coisini.curtain.service.SearchService;
import com.coisini.curtain.util.CommonUtil;
import com.coisini.curtain.vo.SpuSimplifyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description Search 控制器
 * @author coisini
 * @date Aug 26, 2021
 * @Version 1.0
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 根据关键字搜索Spu
     * @param q
     * @param start
     * @param count
     * @return
     */
    @GetMapping("")
    public PagingDozer<Spu, SpuSimplifyVo> search(@RequestParam String q,
                                                  @RequestParam(defaultValue = "0") Integer start,
                                                  @RequestParam(defaultValue = "10") Integer count) {
        PageCounter counter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page = searchService.search(q, counter.getPage(), counter.getCount());
        return new PagingDozer<>(page, SpuSimplifyVo.class);
    }



}
