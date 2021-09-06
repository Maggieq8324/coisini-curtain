package com.coisini.curtain.repository;

import com.coisini.curtain.entity.SpuImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description SpuImg 仓储
 * @author coisini
 * @date Aug 14, 2021
 * @Version 1.0
 */
@Repository
public interface SpuImgRepository extends JpaRepository<SpuImg, Long> {

}
