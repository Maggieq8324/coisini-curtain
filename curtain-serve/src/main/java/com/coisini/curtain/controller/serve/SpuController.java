package com.coisini.curtain.controller.serve;

import com.coisini.curtain.exception.http.NotFoundException;
import com.coisini.curtain.entity.Spu;
import com.coisini.curtain.core.common.PageCounter;
import com.coisini.curtain.core.common.PagingDozer;
import com.coisini.curtain.service.SpuService;
import com.coisini.curtain.util.CommonUtil;
import com.coisini.curtain.vo.SpuVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Positive;
import java.util.Objects;

/**
 * @Description spu控制器
 * @author coisini
 * @date Aug 14, 2021
 * @Version 1.0
 */
@RestController
@RequestMapping("/spu")
@Validated
public class SpuController {

    @Autowired
    private SpuService spuService;

    /**
     * 获取Spu明细
     * @param id
     * @return
     */
    @GetMapping("/id/{id}/detail")
    public Spu getDetail(@PathVariable @Positive(message = "{id.positive}") Long id) {
        Spu spu = spuService.getDetail(id);

        if (Objects.isNull(spu)) {
            throw new NotFoundException(30003);
        }

        return spu;
    }

    /**
     * 获取简化spu明细
     * @param id
     * @return
     */
    @GetMapping("/id/{id}/simplify")
    public SpuVo getSimplifySpu(@PathVariable @Positive Long id) {
        Spu spu = spuService.getDetail(id);
        SpuVo spuVo = new SpuVo();
        BeanUtils.copyProperties(spu, spuVo);
        return spuVo;
    }

    /**
     * 获取Spu分页数据
     * @return
     */
    @GetMapping("/latest")
    public PagingDozer<Spu, SpuVo> getLatestSpuList(@RequestParam(defaultValue = "0") Integer start,
                                        @RequestParam(defaultValue = "10") Integer count) {
        // 分页请求对象
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);

        // 分页返回数据
        Page<Spu> page = spuService.getLatestPagingSpu(pageCounter.getPage(), pageCounter.getCount());

        // 分页拷贝对象返回
        return new PagingDozer<>(page, SpuVo.class);
    }

    /**
     * 获取分类spu
     * @param id
     * @param isRoot
     * @param start
     * @param count
     * @return
     */
    @GetMapping("/by/category/{id}")
    public PagingDozer<Spu, SpuVo> getByCategory(@PathVariable @Positive Long id,
                                                 @RequestParam(name="is_root") Boolean isRoot,
                                                 @RequestParam(name="start", defaultValue = "0") Integer start,
                                                 @RequestParam(name="count", defaultValue = "10") Integer count) {

        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page = spuService.getByCategory(id, isRoot, pageCounter.getPage(), pageCounter.getCount());
        return new PagingDozer<>(page, SpuVo.class);
    }

}
