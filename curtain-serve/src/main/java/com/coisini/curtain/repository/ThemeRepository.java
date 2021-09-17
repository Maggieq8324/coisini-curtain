package com.coisini.curtain.repository;

import com.coisini.curtain.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * @Description Theme 仓储
 * @author coisini
 * @date Aug 14, 2021
 * @Version 1.0
 */
@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    /**
     * 通过names查找
     * @param names
     * @return
     */
    @Query("select t from Theme t where t.name in (:names)")
    List<Theme> findByNames(@Param("names") List<String> names);

    /**
     * 通过name查找
     * @param name
     * @return
     */
    @Query
    Optional<Theme> findByName(String name);

}
