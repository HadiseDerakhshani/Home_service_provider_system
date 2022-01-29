package ir.maktab.data.repasitory;


import ir.maktab.data.model.user.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Integer> {


    Optional<Expert> findByEmail(String email);

/*
    @Transactional
    @Modifying
    @Query(value = "update Expert set credit=:credit where  email=:email")
    void updateCredit(@Param("email") String email, @Param("credit") double credit);

    @Transactional
    @Modifying
    @Query(value = "update Expert set phoneNumber=:phone where  email=:email")
    void updatePhoneNumber(@Param("email") String email, @Param("phone") String phone);

    @Transactional
    @Modifying
    @Query(value = "update Expert set password=:pass where  email=:email")
    void updatePassword(@Param("email") String email, @Param("pass") String pass);

    @Transactional
    @Modifying
    @Query(value = "update Expert set score=:score where  email=:email")
    void updateScore(@Param("email") String email, @Param("score") int score);

    @Transactional
    @Modifying
    @Query(value = "update Expert set userStatus=:status where  email=:email")
    void updateStatus(@Param("email") String email, @Param("status") UserStatus status);

    @Transactional
    @Modifying
    @Query(value = "update Expert set serviceList=:serviceList where email=:email")
    void updateServiceList(@Param("email") String email, @Param("serviceList") List<SubService> serviceList);*/
}