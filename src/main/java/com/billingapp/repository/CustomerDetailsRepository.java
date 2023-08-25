package com.billingapp.repository;

import com.billingapp.entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails,Long> {
    @Query(value = "select * from customer_details where customer_id=:customerId",nativeQuery = true)
    CustomerDetails getById(@Param("customerId")Long customerId);

    @Query(value = "select status from customer_details where customer_id=:customerId",nativeQuery = true)
    Boolean getStatus(@Param("customerId")Long customerId);

    @Transactional
    @Modifying
    @Query(value = "update customer_details set status=:status where customer_id=:customerId",nativeQuery = true)
    Integer updateStatus(@Param("customerId")Long customerId,@Param("status")Boolean status);

    @Query(value = "select * from customer_details where inserted_by_id=:userId",nativeQuery = true)
    List<CustomerDetails> getAllCustomersByUserId(@Param("userId")Long userId);

    @Query(value = "select customer_id,customer_name,customer_email,customer_mobile, "
            +"village from customer_details where customer_mobile=:mobileNo",nativeQuery = true)
    List<Object[]> getCustomerDetailsByMobile(@Param("mobileNo")Long mobileNo);
}
