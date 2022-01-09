package data.dao;

import data.model.serviceSystem.SubService;
import data.model.user.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface SubServiceDao extends JpaRepository<SubService, Integer> {
    @Override
    SubService save(SubService subService);

    @Override
    List<SubService> findAll();

    SubService findBySubServiceMap(Map<String, String> subServiceMap);

    @Transactional
    @Modifying
    @Query(value = "update Expert set expertList=:newValue where id=:id", nativeQuery = true)
    void updateExpertList(@Param("newValue") List<Expert> newValue, @Param("id") int id);
}
