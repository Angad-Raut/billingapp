package com.billingapp.repository;

import com.billingapp.entity.CropDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CropDetailsRepository extends JpaRepository<CropDetails,Long> {

    @Query(value = "select * from crop_details where crop_id=:cropId",nativeQuery = true)
    CropDetails getCropById(@Param("cropId")Long cropId);

    @Query(value = "select status from crop_details where crop_id=:cropId",nativeQuery = true)
    Boolean getStatus(@Param("cropId")Long cropId);

    @Transactional
    @Modifying
    @Query(value = "update crop_details set status=:status where crop_id=:cropId",nativeQuery = true)
    Integer updateStatus(@Param("cropId")Long cropId,@Param("status")Boolean status);

    @Query(value = "select * from crop_details where inserted_id=:userId",nativeQuery = true)
    List<CropDetails> getAllCropDetailsByUserId(@Param("userId")Long userId);

    @Query(value = "select crop_name from crop_details where crop_id=:cropId",nativeQuery = true)
    String getCropName(@Param("cropId")Long cropId);

    @Query(value = "select crop_id,crop_name,crop_price from crop_details where inserted_id=:userId",nativeQuery = true)
    List<Object[]> getCropDropDown(@Param("userId")Long userId);
}
