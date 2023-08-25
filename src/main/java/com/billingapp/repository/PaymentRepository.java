package com.billingapp.repository;

import com.billingapp.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDetails,Long> {
    @Query(value = "select * from payment_details where payment_id=:paymentId",nativeQuery = true)
    PaymentDetails getByPaymentId(@Param("paymentId")Long paymentId);

    @Transactional
    @Modifying
    @Query(value = "update payment_details set paid_amount=:amount,updated_by=:userType,updated_by_id=:userId,payment_status=:paymentStatus where payment_id=:paymentId",nativeQuery = true)
    Integer updatePayment(@Param("paymentId")Long paymentId,@Param("amount")Double amount,@Param("paymentStatus")Integer paymentStatus,
                          @Param("userId")Long userId,@Param("userType")Integer userType);
}
