package com.coisini.curtain.service.impl;

import com.coisini.curtain.entity.SaleExplain;
import com.coisini.curtain.repository.SaleExplainRepository;
import com.coisini.curtain.service.SaleExplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Description Sale Explain 实现类
 * @author coisini
 * @date Aug 26, 2021
 * @Version 1.0
 */
@Service
public class SaleExplainServiceImple implements SaleExplainService {

    @Autowired
    private SaleExplainRepository saleExplainRepository;

    @Override
    public List<SaleExplain> getByFixed() {
        return saleExplainRepository.findAllByFixed(true);
    }
}
