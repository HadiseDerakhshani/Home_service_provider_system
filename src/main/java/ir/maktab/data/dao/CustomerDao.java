package ir.maktab.data.dao;


import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {

    Customer save(Customer customer);

    void delete(Customer customer);

    List<Customer> findByUserStatus(UserStatus status);

    Customer findByEmail(String email);

    @Override
    List<Customer> findAll();

    @Transactional
    @Modifying
    @Query(value = "update Customer set phoneNumber=:phone where  email=:email", nativeQuery = true)
    void updatePhoneNumber(@Param("email") String email, @Param("phone") String phone);

    @Transactional
    @Modifying
    @Query(value = "update Customer set password=:pass where  email=:email", nativeQuery = true)
    void updatePassword(@Param("email") String email, @Param("pass") String pass);

    @Transactional
    @Modifying
    @Query(value = "update Customer set credit=:credit where  email=:email", nativeQuery = true)
    void updateCredit(@Param("email") String email, @Param("credit") double credit);

    @Transactional
    @Modifying
    @Query(value = "update Customer set userStatus=:status where  email=:email", nativeQuery = true)
    void updateStatus(@Param("email") String email, @Param("status") UserStatus status);

}
