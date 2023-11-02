package com.balancegame.balancegameproject.repository;

import com.balancegame.balancegameproject.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
