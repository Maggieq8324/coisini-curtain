package com.coisini.curtain.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.common.util.PageUtil;
import com.coisini.curtain.model.CUserDO;
import com.coisini.curtain.service.CUserService;
import com.coisini.curtain.vo.PageResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/user")
@Validated
public class CUserController {

    @Autowired
    private CUserService userService;

    @GetMapping("/{id}")
    public CUserDO get(@PathVariable @Positive(message = "{id}") Integer id) {
        return userService.getParsedUserById(id);
    }

    @GetMapping("/page")
    public PageResponseVO<CUserDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{count}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page}") Integer page
    ) {
        IPage<CUserDO> paging = userService.getUserByPage(count, page);
        return PageUtil.build(paging);
    }

    @GetMapping("/search")
    public PageResponseVO<CUserDO> search(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{count}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page}") Integer page,
            @RequestParam(name = "keyword") String keyword
    ) {
        IPage<CUserDO> paging = userService.search(page, count, keyword);
        return PageUtil.build(paging);
    }
}
