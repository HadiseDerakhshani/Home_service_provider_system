package ir.maktab.data.dao;


import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.data.model.user.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExpertDao extends JpaRepository<Expert, Integer> {


    Expert save(Expert expert);

    void delete(Expert expert);

    Expert findByEmail(String email);

    @Override
    List<Expert> findAll();

    @Transactional
    @Modifying
    @Query(value = "update Expert set credit=:credit where  email=:email", nativeQuery = true)
    void updateCredit(@Param("email") String email, @Param("credit") double credit);

    @Transactional
    @Modifying
    @Query(value = "update Expert set phoneNumber=:phone where  email=:email", nativeQuery = true)
    void updatePhoneNumber(@Param("email") String email, @Param("phone") String phone);

    @Transactional
    @Modifying
    @Query(value = "update Expert set password=:pass where  email=:email", nativeQuery = true)
    void updatePassword(@Param("email") String email, @Param("pass") String pass);

    @Transactional
    @Modifying
    @Query(value = "update Expert set score=:score where  email=:email", nativeQuery = true)
    void updateScore(@Param("email") String email, @Param("score") int score);

    @Transactional
    @Modifying
    @Query(value = "update Expert set userStatus=:status where  email=:email", nativeQuery = true)
    void updateStatus(@Param("email") String email, @Param("status") UserStatus status);

    @Transactional
    @Modifying
    @Query(value = "update Expert set serviceList=:serviceList where email=:email", nativeQuery = true)
    void updateServiceList(@Param("email") String email, @Param("serviceList") List<SubService> serviceList);
}