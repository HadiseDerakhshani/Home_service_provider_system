package ir.maktab.data.dao;

import ir.maktab.data.model.serviceSystem.Service;
import ir.maktab.data.model.serviceSystem.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ServiceDao extends JpaRepository<Service, Integer> {


    Optional<Service> finByName(String name);

    @Transactional
    @Modifying
    @Query(value = "update Service set subServiceList=:subServiceList where id=:id")
    void updateSubList(@Param("id") int id, @Param("subServiceList") Set<SubService> subServiceList);
}
