package com.coisini.curtain.service.impl;

import com.coisini.curtain.entity.Tag;
import com.coisini.curtain.repository.TagRepository;
import com.coisini.curtain.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Description Tag 实现类
 * @author coisini
 * @date Aug 26, 2021
 * @Version 1.0
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> getTagsByType() {
        return tagRepository.findAllByType(true);
    }
}
