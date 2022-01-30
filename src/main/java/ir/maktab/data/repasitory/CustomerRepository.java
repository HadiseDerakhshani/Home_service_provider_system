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

   /* @Transactional
    @Modifying
    @Query(value = "update Customer set phoneNumber=:phone where  email=:email")
    void updatePhoneNumber(@Param("email") String email, @Param("phone") String phone);*/

  /*  @Transactional
    @Modifying
    @Query(value = "update Customer set password=:pass where  email=:email")
    void updatePassword(@Param("email") String email, @Param("pass") String pass);

    @Transactional
    @Modifying
    @Query(value = "update Customer set credit=:credit where  email=:email")
    void updateCredit(@Param("email") String email, @Param("credit") double credit);

    @Transactional
    @Modifying
    @Query(value = "update Customer set userStatus=:status where  email=:email")
    void updateStatus(@Param("email") String email, @Param("status") UserStatus status);*/

}
