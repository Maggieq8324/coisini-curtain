package com.coisini.curtain.controller.serve;

import com.coisini.curtain.entity.Tag;
import com.coisini.curtain.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Description Tag 控制器
 * @author coisini
 * @date Aug 26, 2021
 * @Version 1.0
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/type")
    public List<Tag> getTagsByType() {
        return tagService.getTagsByType();
    }



}
