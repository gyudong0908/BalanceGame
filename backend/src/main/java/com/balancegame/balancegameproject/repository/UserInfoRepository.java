package com.balancegame.balancegameproject.repository;

import com.balancegame.balancegameproject.entity.Userinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserInfoRepository extends JpaRepository<Userinfo, Integer> {
    Userinfo getByEmail(String email);
    @Transactional
    @Modifying
    @Query("update Userinfo u set u.point = u.point + 1000")
    void addUserDefaultPoint();
}
