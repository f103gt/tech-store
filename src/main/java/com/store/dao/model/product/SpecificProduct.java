package com.store.dao.model.product;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

@Entity
@Table(name = "specific_product")
public class SpecificProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    private String sku;
    private Integer quantity;
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_name",referencedColumnName = "product_name", unique = true)
    private Product product;

    @OneToMany(mappedBy = "specificProduct",cascade = CascadeType.PERSIST, orphanRemoval = false,fetch = FetchType.EAGER)
    private Set<Image> images;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }


    public void setProduct(Product product) {
        this.product = product;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
}
