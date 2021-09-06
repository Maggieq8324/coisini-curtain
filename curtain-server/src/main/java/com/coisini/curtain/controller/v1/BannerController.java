package com.coisini.curtain.controller.v1;

import com.coisini.curtain.exception.http.NotFoundException;
import com.coisini.curtain.entity.Banner;
import com.coisini.curtain.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * @Description Banner控制器
 * @author coisini
 * @date Aug 11, 2021
 * @Version 1.0
 */
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * 通过名称查找
     * @param name
     * @return
     */
    @GetMapping("/name/{name}")
//    @ScopeLevel()
    public Banner getByName(@PathVariable @NotBlank String name) {
        Banner banner = bannerService.getByName(name);
        if (Objects.isNull(banner)) {
            throw new NotFoundException(30005);
        }

        return banner;
    }

}
