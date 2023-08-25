package com.billingapp.repository;

import com.billingapp.entity.DiscountSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountSetting,Long> {

    @Query(value = "select * from discount_setting where id=:id",nativeQuery = true)
    DiscountSetting getById(@Param("id")Long id);

    @Query(value = "select * from discount_setting where inserted_by_id=:userId",nativeQuery = true)
    List<DiscountSetting> getAllDiscountsByUserId(@Param("userId")Long userId);

    @Query(value = "select id,discount_value from discount_setting where inserted_by_id=:userId and discount_type=:type",nativeQuery = true)
    List<Object[]> getDiscountDropDownByType(@Param("userId")Long userId,@Param("type")Integer type);
}
