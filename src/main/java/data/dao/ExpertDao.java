package data.dao;


import data.model.enums.UserStatus;
import data.model.user.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertDao extends JpaRepository<Expert, Integer> {

    @Override
    Expert save(Expert expert);

    @Override
    void delete(Expert expert);

    List<Expert> findByFirstNameOrLastNameOrEmailOrServiceList(String name, String family, String email, List<data.model.serviceSystem.Service> list);

    List<Expert> findByUserStatus(UserStatus status);

    Expert findByEmail(String email);

    Expert findByEmailAndUserStatus(String email, UserStatus status);

    @Override
    List<Expert> findAll();
/*   @Transactional
    @Modifying
    @Query(value = "update Expert set userStatus=:nawValue where email=:email", nativeQuery = true)
    void updateStatus(@Param("newValue") UserStatus newValue, @Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "update Expert set phonenumber=:newValue where email=:email", nativeQuery = true)
    void updatePhoneNumber(@Param("newValue") String phoneNumber, @Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "update Expert set credit=:newValue where email=:email", nativeQuery = true)
    void updateCredit(@Param("newValue") double newValue, @Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "update Expert set password=:newValue where email=:email", nativeQuery = true)
    void updatePassword(@Param("newValue") String newValue, @Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "update Expert set serviceList=:newValue where email=:email", nativeQuery = true)
    void updateServiceList(@Param("newValue") List<SubService> newValue, @Param("email") String email);*/
}
