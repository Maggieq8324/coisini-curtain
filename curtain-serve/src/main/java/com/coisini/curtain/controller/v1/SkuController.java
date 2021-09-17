package com.coisini.curtain.controller.v1;

import com.coisini.curtain.entity.Sku;
import com.coisini.curtain.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description Sku控制器
 * @author coisini
 * @date Aug 30, 2021
 * @Version 1.0
 */
@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    /**
     * 通过id集合获取sku
     * @param ids
     * @return
     */
    @GetMapping("")
    public List<Sku> getSkuListInIds(@RequestParam(name = "ids", required = false) String ids) {

        if(ids == null || ids.isEmpty()){
            return Collections.emptyList();
        }

        List<Long> idList = Arrays.stream(ids.split(","))
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());

        return skuService.getSkuListByIds(idList);
    }

}
