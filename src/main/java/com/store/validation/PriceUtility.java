package com.store.validation;

import com.store.model.product.CartItem;
import com.store.model.product.Category;
import com.store.model.product.Product;
import com.store.model.product.Promotion;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PriceUtility {

    public BigDecimal calculateTotalPrice(List<CartItem> cartItems){
        return  cartItems.stream()
                .map(cartItem -> cartItem.getFinalPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        //BigDecimal.add = (subtotal, itemPrice) -> subtotal.add(itemPrice)
    }

    public BigDecimal calculatePrice(Category category, Product product){
        return product.getPrice().
                subtract(
                        product.getPrice().multiply(
                                this.calculateDiscountRate(category)
                        ).divide(BigDecimal.valueOf(100), RoundingMode.CEILING));
    }
    public BigDecimal calculateDiscountRate(Category category){
        BigDecimal discountRate = BigDecimal.ZERO;
        if (category.getPromotions().size() != 0) {
            Promotion promotion = category.getPromotions().iterator().next();
            if (!LocalDateTime.now().isBefore(promotion.getStartDate()) &&
                    !LocalDateTime.now().isAfter(promotion.getEndDate())) {
                discountRate = BigDecimal.valueOf(promotion.getDiscountRate());
            }
        }
        return discountRate;
    }
}
