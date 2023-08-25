package com.billingapp.repository;

import com.billingapp.entity.CropSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CropSettingRepository extends JpaRepository<CropSetting,Long> {

    @Query(value = "select * from crop_setting where id=:id",nativeQuery = true)
    CropSetting getById(@Param("id")Long id);

    @Query(value = "select status from crop_setting where id=:id",nativeQuery = true)
    Boolean getStatus(@Param("id")Long id);

    @Transactional
    @Modifying
    @Query(value = "update crop_setting set status=:status where id=:id",nativeQuery = true)
    Integer updateStatus(@Param("id")Long id, @Param("status")Boolean status);

    @Query(value = "select * from crop_setting where inserted_by_id=:userId",nativeQuery = true)
    List<CropSetting> getAllCropSettingByUserId(@Param("userId")Long userId);

    @Query(value = "select * from crop_setting where inserted_by_id=:userId",nativeQuery = true)
    CropSetting getByUserId(@Param("userId")Long userId);
}
