package ir.maktab.data.repasitory;

import ir.maktab.data.entity.serviceSystem.SubService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubServiceRepository extends JpaRepository<SubService, Integer> {
    Optional<SubService> findByName(String name);


}
