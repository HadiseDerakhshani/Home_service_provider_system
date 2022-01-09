package data.dao;

import data.model.serviceSystem.SubService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface SubServiceDao extends JpaRepository<SubService, Integer> {
    @Override
    SubService save(SubService subService);

    @Override
    List<SubService> findAll();

    SubService findBySubServiceMap(Map<String, String> subServiceMap);
}
