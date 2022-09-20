package org.ksushka.bse.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bse_order")
public class Order {
    public Order() {}

    public Order(Integer letycloneUserId) {
        this.letycloneUserId = letycloneUserId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer letycloneUserId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderedItem> items;

    public int getId() {
        return id;
    }

    public Integer getLetycloneUserId() {
        return letycloneUserId;
    }

    public List<OrderedItem> getItems() {
        return items;
    }
}
