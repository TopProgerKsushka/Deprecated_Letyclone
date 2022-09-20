package org.ksushka.bse.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderedItemRepository extends JpaRepository<OrderedItem, Integer> {
    OrderedItem findById(int id);
    List<OrderedItem> findAllByOrder(Order order);
}
