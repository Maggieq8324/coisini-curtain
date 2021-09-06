package com.coisini.curtain.repository;

import com.coisini.curtain.entity.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description Spu 仓储
 * @author coisini
 * @date Aug 14, 2021
 * @Version 1.0
 */
@Repository
public interface SpuRepository extends JpaRepository<Spu, Long> {

    /**
     * 通过ID查找
     * @param id ID
     * @return
     */
    Spu findOneById(Long id);

    /**
     *
     * @param ids
     * @return
     */
    List<Spu> findByIdIn(List<Long> ids);

    /**
     * 通过分类ID查询
     * @param cid 分类ID
     * @param page 分页
     * @return
     */
    Page<Spu> findByCategoryIdOrderByCreateTimeDesc(Long cid, Pageable page);

    /**
     * 通过父级分类ID查询
     * @param cid 父级分类ID
     * @param page 分页
     * @return
     */
    Page<Spu> findByRootCategoryIdOrderByCreateTime(Long cid, Pageable page);

    /**
     * 根据title模糊查询
     * @param keyword
     * @param keyword1
     * @param pageable
     * @return
     */
    Page<Spu> findByTitleLikeOrSubtitleLike(String keyword, String keyword1, Pageable pageable);

}
