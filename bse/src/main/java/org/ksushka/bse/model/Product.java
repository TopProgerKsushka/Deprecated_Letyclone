package org.ksushka.bse.model;

import javax.persistence.*;

@Entity
@Table(name = "bse_product")
public class Product {
    public Product() {}
    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;
    private Double cashbackPercent;
    private String pictureUrl;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Double getCashbackPercent() {
        return cashbackPercent;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}