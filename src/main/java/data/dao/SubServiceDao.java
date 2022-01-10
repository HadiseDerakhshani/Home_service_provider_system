package data.dao;

import data.model.serviceSystem.SubService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubServiceDao extends JpaRepository<SubService, Integer> {
    @Override
    SubService save(SubService subService);

    @Override
    List<SubService> findAll();

    SubService findByName(String name);

    @Override
    void delete(SubService subService);
    /*  @Transactional
    @Modifying
    @Query(value = "update Expert set expertList=:newValue where id=:id", nativeQuery = true)
    void updateExpertList(@Param("newValue") List<Expert> newValue, @Param("id") int id);*/
}
