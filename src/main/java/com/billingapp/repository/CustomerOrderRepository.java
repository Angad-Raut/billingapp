package com.billingapp.repository;

import com.billingapp.entity.CustomerOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrderDetails,Long> {
    @Query(value = "select order_number from customer_order_details order by order_id desc limit 1",nativeQuery = true)
    String getLastOrderNumber();

    @Query(value = "select cr.order_number,c.customer_name,c.customer_mobile,c.customer_email,c.village, "
            +"cr.order_total_amt,cr.order_discount_type,cr.order_discount, "
            +"cr.order_discounted_amt,cr.inserted_time,p.payment_status from customer_order_details cr  "
            +"join payment_details p on cr.payment_id=p.payment_id "
            +"join customer_details c on cr.customer_id = c.customer_id "
            +"where c.inserted_by_id=cr.inserted_by_id and c.inserted_by=cr.inserted_by "
            +"and cr.order_id=:orderId",nativeQuery = true)
    List<Object[]> getOrderDetailsByOrderId(@Param("orderId")Long orderId);

    @Query(value = "select cr.order_id,cr.order_number,c.customer_name,c.customer_mobile, "
            +"cr.inserted_time,cr.order_total_amt,cr.order_discounted_amt from customer_order_details cr "
            +"join customer_details c on cr.customer_id = c.customer_id "
            +"where c.inserted_by_id=cr.inserted_by_id and cr.inserted_by_id=:userId "
            +"and cr.inserted_by=21",nativeQuery = true)
    List<Object[]> getAllCustomersOrdersByUserId(@Param("userId")Long userId);

    @Query(value = "select crop.crop_name,item.rate,item.quantity, "
            +"item.weight,item.total_amount from order_items item "
            +"join crop_details crop on item.crop_id=crop.crop_id "
            +"join customer_order_details cr on item.order_id=cr.order_id "
            +"where cr.order_id=:orderId",nativeQuery = true)
    List<Object[]> getOrderItems(@Param("orderId")Long orderId);

    @Query(value = "select p.payment_id,cr.order_number,cr.inserted_time,cr.order_total_amt, "
            +"cr.order_discounted_amt,p.paid_amount,p.payment_status from customer_order_details cr "
            +"join payment_details p on cr.payment_id=p.payment_id "
            +"where cr.order_id=:orderId",nativeQuery = true)
    List<Object[]> getOrderAndPaymentDetails(@Param("orderId")Long orderId);
}
