package ir.maktab.data.dao;

import ir.maktab.data.model.user.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerDao extends JpaRepository<Manager, Integer> {
    Manager save(Manager manager);

    Manager findByUsernameAndPassword(String name, String pass);
}
