package com.coisini.curtain.controller.v1;

import com.coisini.curtain.entity.SaleExplain;
import com.coisini.curtain.service.SaleExplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description Sale Explain 控制器
 * @author coisini
 * @date Aug 26, 2021
 * @Version 1.0
 */
@RestController
@RequestMapping("/sale_explain")
public class SaleExplainController {

    @Autowired
    private SaleExplainService saleExplainService;

    @GetMapping(value = "/fixed")
    public List<SaleExplain> getByFixed() {
        return saleExplainService.getByFixed();
    }


}
