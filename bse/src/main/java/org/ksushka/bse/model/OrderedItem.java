package org.ksushka.bse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "bse_ordered_item")
public class OrderedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(nullable = false)
    private Boolean canBeRefunded = true;

    @Column(nullable = false)
    private Boolean refunded = false;

    public void setCanBeRefunded(Boolean canBeRefunded) {
        this.canBeRefunded = canBeRefunded;
    }

    public void setRefunded(Boolean refunded) {
        this.refunded = refunded;
    }

    public OrderedItem() {}

    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public Boolean getCanBeRefunded() {
        return canBeRefunded;
    }

    public Boolean getRefunded() {
        return refunded;
    }

    public OrderedItem(Order order, Product product) {
        this.order = order;
        this.product = product;
    }
}
