package com.coisini.curtain.repository;

import com.coisini.curtain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @Description Tag 仓储
 * @author coisini
 * @date Aug 26, 2021
 * @Version 1.0
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * 根据Type查找
     * @param type
     * @return
     */
    List<Tag> findAllByType(Boolean type);

}
