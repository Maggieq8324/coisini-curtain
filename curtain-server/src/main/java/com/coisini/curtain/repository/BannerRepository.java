package com.coisini.curtain.repository;

import com.coisini.curtain.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description Banner 仓储
 * @author coisini
 * @date
 * @Version 1.0
 */
@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    /**
     * 通过ID查找
     * @param id
     * @return
     */
    Banner findOneById(Long id);

    /**
     * 通过Name查找
     * @param name
     * @return
     */
    Banner findONeByName(String name);

}
