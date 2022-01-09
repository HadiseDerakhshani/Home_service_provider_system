package data.dao;


import data.model.enums.UserStatus;
import data.model.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service//باید بردارم
@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {
    @Override
    Customer save(Customer customer);

    @Override
    Optional<Customer> findById(Integer id);

    @Override
    void deleteById(Integer id);

    @Override
    void delete(Customer customer);

    List<Customer> findByFirstNameOrLastNameOrEmail(String name, String family, String email);

    List<Customer> findByUserStatus(UserStatus status);

    Customer findByEmail(String email);

    Customer findByEmailAndUserStatus(String email, UserStatus status);

    @Transactional
    @Modifying
    @Query(value = "update Customer set userStatus=:nawValue where email=:email", nativeQuery = true)
    void updateStatus(@Param("newValue") UserStatus newValue, @Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "update Customer set phonenumber=:newValue where email=:email", nativeQuery = true)
    void updateLastName(@Param("newValue") String phoneNumber, @Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "update Customer set credit=:newValue where email=:email", nativeQuery = true)
    void updateCredit(@Param("newValue") double newValue, @Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "update Customer set password=:newValue where email=:email", nativeQuery = true)
    void updatePassword(@Param("newValue") String newValue, @Param("email") String email);
}
