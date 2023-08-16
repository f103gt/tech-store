package com.store.dao.model.product;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_name"}))
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "category_name",referencedColumnName = "category_name", unique = true)
    private Category category;
    @Column(name = "product_name")
    private String productName;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, orphanRemoval = false,fetch = FetchType.EAGER)
    private List<Image> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE, orphanRemoval = false,fetch = FetchType.EAGER)
    private Set<SpecificProduct> specificProducts;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Set<SpecificProduct> getSpecificProducts() {
        return specificProducts;
    }

    public void setSpecificProducts(Set<SpecificProduct> specificProducts) {
        this.specificProducts = specificProducts;
    }

}
