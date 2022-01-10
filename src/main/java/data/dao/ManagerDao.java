package data.dao;

import data.model.user.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerDao extends JpaRepository<Manager, Integer> {
    @Override
    Manager save(Manager manager);

    Manager findByUsername(String userName);

    boolean findByUsernameAndPassword(String name, String pass);

    Manager findByPassword(String password);

    boolean exists(Manager manager);
}
