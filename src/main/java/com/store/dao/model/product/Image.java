package com.store.dao.model.product;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.math.BigInteger;

@Entity
@Table(name="image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name="image_data")
    private byte[] imageData;

   /* @Column("primary_image")
    private boolean primaryImage;*/

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "product_name",referencedColumnName = "product_name", unique = true)
    private Product product;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "specific_product_sku",referencedColumnName = "sku", unique = true)
    private SpecificProduct specificProduct;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public SpecificProduct getSpecificProduct() {
        return specificProduct;
    }

    public void setSpecificProduct(SpecificProduct specificProduct) {
        this.specificProduct = specificProduct;
    }

   /* public boolean isPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(boolean primaryImage) {
        this.primaryImage = primaryImage;
    }*/
}
