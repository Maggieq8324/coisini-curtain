package com.coisini.curtain.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coisini.curtain.common.util.PageUtil;
import com.coisini.curtain.model.ClientUser;
import com.coisini.curtain.service.CUserService;
import com.coisini.curtain.vo.PageResponseVo;
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
    public ClientUser get(@PathVariable @Positive(message = "{id}") Integer id) {
        return userService.getParsedUserById(id);
    }

    @GetMapping("/page")
    public PageResponseVo<ClientUser> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{count}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page}") Integer page
    ) {
        IPage<ClientUser> paging = userService.getUserByPage(count, page);
        return PageUtil.build(paging);
    }

    @GetMapping("/search")
    public PageResponseVo<ClientUser> search(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{count}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page}") Integer page,
            @RequestParam(name = "keyword") String keyword
    ) {
        IPage<ClientUser> paging = userService.search(page, count, keyword);
        return PageUtil.build(paging);
    }
}
