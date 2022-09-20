package org.ksushka.letyclone.emailservice.model;

import javax.persistence.*;

@Entity
@Table(name = "lc_item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Double cashback;

    @Column(nullable = false)
    private Boolean canBeRefunded;

    @Column(nullable = false)
    private Boolean refunded;

    @Column(nullable = false)
    private Integer shopOrderedItemId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;

    public Item() {}

    public Item(User user, Shop shop, double cashback, boolean canBeRefunded, boolean refunded, int shopOrderedItemId) {
        this.user = user;
        this.shop = shop;
        this.cashback = cashback;
        this.canBeRefunded = canBeRefunded;
        this.refunded = refunded;
        this.shopOrderedItemId = shopOrderedItemId;
    }

    public Double getCashback() {
        return cashback;
    }

    public int getId() {
        return id;
    }

    public Boolean getCanBeRefunded() {
        return canBeRefunded;
    }

    public Boolean getRefunded() {
        return refunded;
    }

    public Integer getShopOrderedItemId() {
        return shopOrderedItemId;
    }

    public User getUser() {
        return user;
    }

    public Shop getShop() {
        return shop;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCashback(Double cashback) {
        this.cashback = cashback;
    }

    public void setCanBeRefunded(Boolean canBeRefunded) {
        this.canBeRefunded = canBeRefunded;
    }

    public void setRefunded(Boolean refunded) {
        this.refunded = refunded;
    }

    public void setShopOrderedItemId(Integer shopOrderedItemId) {
        this.shopOrderedItemId = shopOrderedItemId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}

