package com.store.repository.cart;

import com.store.model.product.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, BigInteger> {
    @Query("select ci from CartItem ci where ci.product.id = :productId")
    Optional<CartItem> findCartItemByProductId(BigInteger productId);

    @Query("update CartItem ci set ci.quantity = :quantity where ci.id = :cartItemId")
    void updateCartItemQuantityByCartItemId(@Param("cartItemId") BigInteger cartItemId, @Param("quantity") Integer quantity);

    @Query("select ci from CartItem ci where ci.user.id = :userId")
    List<CartItem> findAllCartItemsByUserId(@Param("userId") BigInteger userId);

    void saveAll(List<CartItem> cartItems);
}
