package com.coisini.curtain.repository;

import com.coisini.curtain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * @Description User 仓储
 * @author coisini
 * @date Aug 14, 2021
 * @Version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Optional<User> findByOpenid(String openid);

    User findFirstById(Long id);

    User findByUnifyUid(Long uuid);

}
