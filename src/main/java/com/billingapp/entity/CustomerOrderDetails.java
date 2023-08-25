package com.billingapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "customer_order_details")
public class CustomerOrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String orderNumber;
    private Long customerId;
    private Double orderTotalAmt;
    private Double orderDiscount;
    private Integer orderDiscountType;
    private Double orderDiscountedAmt;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<CustomerOrderItems> customerOrderItems;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "paymentId")
    private PaymentDetails paymentDetails;
    private Boolean status;
    private Long insertedById;
    private Integer insertedBy;
    private Date insertedTime;
}
