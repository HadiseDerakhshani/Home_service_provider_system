package data.dao;


import data.model.enums.UserStatus;
import data.model.user.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Repository
public interface ExpertDao extends JpaRepository<Expert, Integer> {

    @Override
    Expert save(Expert expert);

    @Override
    Optional<Expert> findById(Integer id);

    @Override
    void deleteById(Integer id);

    @Override
    void delete(Expert expert);

    List<Expert> findByFirstNameOrLastNameOrEmailOrServiceList(String name, String family, String email, List<data.model.serviceSystem.Service> list);

    List<Expert> findByUserStatus(UserStatus status);

    Expert findByEmail(String email);

    Expert findByEmailAndUserStatus(String email, UserStatus status);

    @Transactional
    @Modifying
    @Query(value = "update Expert set userStatus=:nawValue where email=:email", nativeQuery = true)
    void updateStatus(@Param("newValue") UserStatus newValue, @Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "update Expert set phonenumber=:newValue where email=:email", nativeQuery = true)
    void updateLastName(@Param("newValue") String phoneNumber, @Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "update Expert set credit=:newValue where email=:email", nativeQuery = true)
    void updateCredit(@Param("newValue") double newValue, @Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "update Expert set password=:newValue where email=:email", nativeQuery = true)
    void updatePassword(@Param("newValue") String newValue, @Param("email") String email);


}
