package com.balancegame.balancegameproject.repository;

import com.balancegame.balancegameproject.entity.Choice;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChoiceRepository extends JpaRepository<Choice,  Integer> {
    @Query("select sum(i.investPoint) from Choice c join Invenstor i  on c.id = i.choice.id where c = :choice")
    Integer getTotalPointByChoice(@Param("choice") Choice choice);
}
