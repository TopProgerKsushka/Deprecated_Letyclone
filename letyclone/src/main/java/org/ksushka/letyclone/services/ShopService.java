package org.ksushka.letyclone.services;

import org.ksushka.letyclone.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ShopService {
    @Autowired
    ShopRepository shopRepository;
    @Transactional
    public List<Shop> getShops() {
        List<Shop> shops = shopRepository.findAll();
        return shops;
    }
}
