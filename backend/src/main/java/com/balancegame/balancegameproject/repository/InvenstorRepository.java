package com.balancegame.balancegameproject.repository;

import com.balancegame.balancegameproject.entity.Invenstor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface InvenstorRepository extends JpaRepository<Invenstor, Integer> {
    @Modifying
    @Transactional
    @Query(value = "update userinfo join invenstor on invenstor.user_id = userinfo.id set userinfo.point = userinfo.point+(invenstor.invest_point*:rate) where invenstor.choice_id = :choiceId", nativeQuery = true)
    void dividePoint(@Param("rate") double rate, @Param("choiceId") Integer choiceId);
}
