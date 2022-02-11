package ir.maktab.data.repasitory;


import ir.maktab.data.entity.enums.UserStatus;
import ir.maktab.data.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    List<Customer> findByUserStatus(UserStatus status);

    Optional<Customer> findByEmail(String email);


}
