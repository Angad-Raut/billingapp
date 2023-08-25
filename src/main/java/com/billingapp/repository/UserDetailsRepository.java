package com.billingapp.repository;

import com.billingapp.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<Users,Long> {

    @Query(value = "select * from user_details where user_id=:userId",nativeQuery = true)
    Users getByUserId(@Param("userId")Long userId);

    @Query(value = "select * from user_details where mobile_number=:mobile",nativeQuery = true)
    Users getUserByMobile(@Param("mobile")Long mobile);
}
