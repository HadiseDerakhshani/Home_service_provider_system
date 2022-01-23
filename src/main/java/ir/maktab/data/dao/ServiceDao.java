package ir.maktab.data.dao;

import ir.maktab.data.model.serviceSystem.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceDao extends JpaRepository<Service, Integer> {


    Optional<Service> findByName(String name);

   /* @Transactional
    @Modifying
    @Query(value = "update Service set subServiceList=:subServiceList where id=:id")
    void updateSubList(@Param("id") int id, @Param("subServiceList") Set<SubService> subServiceList);*/
}
