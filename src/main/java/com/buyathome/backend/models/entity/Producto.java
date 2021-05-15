package com.buyathome.backend.models.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Table(name = "product")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(unique = true)
    private String productName;

    private String detail;

    private String model;

    @Column(nullable = false)
    private BigDecimal price;

    private Integer stock;

    @Column(name = "store_available")
    private Integer storeAvailable;

    @Column(name = "delivery_available")
    private Integer deliveryAvailable;

    private String image;



    public Integer getProductId(){return  productId;}

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStoreAvailable() {
        return storeAvailable;
    }

    public void setStoreAvailable(Integer storeAvailable) {
        this.storeAvailable = storeAvailable;
    }

    public Integer getDeliveryAvailable() {
        return deliveryAvailable;
    }

    public void setDeliveryAvailable(Integer deliveryAvailable) {
        this.deliveryAvailable = deliveryAvailable;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    private static final long serialVersionUID = 1L;
}
