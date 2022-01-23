package ir.maktab.data.dao;

import ir.maktab.data.model.serviceSystem.SubService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubServiceDao extends JpaRepository<SubService, Integer> {
    Optional<SubService> findByName(String name);

   /* @Transactional
    @Modifying
    @Query(value = "update SubService set expertList=:expertList where id=:id")
    void updateExpertList(@Param("id") int id, @Param("expertList") List<Expert> expertList);*/
}
