package com.store.repository.promotion;

import com.store.model.product.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface PromotionRepository extends JpaRepository<Promotion, BigInteger> {

}
