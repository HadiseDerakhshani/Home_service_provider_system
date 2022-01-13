package ir.maktab.data.dao;

import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.data.model.user.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SubServiceDao extends JpaRepository<SubService, Integer> {

    SubService save(SubService subService);


    List<SubService> findAll();

    SubService findByName(String name);


    void delete(SubService subService);

    @Transactional
    @Modifying
    @Query(value = "update SubService set expertList=:expertList where id=:id", nativeQuery = true)
    void updateExpertList(@Param("id") int id, @Param("expertList") List<Expert> expertList);
}
