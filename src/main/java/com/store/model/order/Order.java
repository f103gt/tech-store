package com.store.model.order;

import java.io.Serializable;

/*@Entity
@Table(name="shop_order")*/
public class Order implements Serializable {
   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @ManyToOne
    private final User user;

    @Column(name="order_data")
    private final LocalDateTime orderDate;

    //TODO create an implication informing user that the order payment is being proceeded
    //TODO accordingly to the delivery service during the process of receiving order

    @Column(name="shipping_address")
    private final Address shippingAddress;

    @Column(name="shipping_method")
    private final Shipping shippingMethod;

    @Column(name="order_total")
    private final BigDecimal orderTotal;

    @Column(name="order_status")
    private final OrderStatus orderStatus;
*/
}
