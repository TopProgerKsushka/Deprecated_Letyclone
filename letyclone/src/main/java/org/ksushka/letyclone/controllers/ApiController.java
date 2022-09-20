package org.ksushka.letyclone.controllers;

import org.ksushka.letyclone.messages.*;
import org.ksushka.letyclone.model.*;
import org.ksushka.letyclone.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {
    //EntityManagerFactory emf = Persistence.createEntityManagerFactory("studs");
//    @PersistenceContext
//    EntityManager em;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShopRepository shopRepository;

    @Autowired
    UserService userService;
    @Autowired
    ShopService shopService;

    @Autowired
    CashbackService cashbackService;

    @GetMapping(value = "get_info", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetAccountInfoResponse getAccountInfo(@RequestParam(required = false) Integer userId) {
        try {
            return GetAccountInfoResponse.ok(userService.getAccountInfo(userId));
        } catch (RuntimeException ex) {
            return GetAccountInfoResponse.error(ex.getLocalizedMessage());
        }
    }

    @GetMapping(value = "get_shops", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetShopsResponse getShops() {
        return GetShopsResponse.ok(shopService.getShops());
    }

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login(@RequestBody LoginRequest req) {
        try {
            return LoginResponse.ok(userService.login(req.username, req.password));
        } catch (RuntimeException ex) {
            return LoginResponse.error(ex.getLocalizedMessage());
        }
    }

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RegisterResponse register(@RequestBody RegisterRequest req) {
        try {
            return RegisterResponse.ok(userService.register(req.username, req.password));
        } catch (RuntimeException ex) {
            return RegisterResponse.error(ex.getLocalizedMessage());
        }
    }

    @PostMapping(value = "private/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GetUserResponse withdraw(@RequestBody GetUserRequest req) {
        try {
            cashbackService.withdraw(req.userId);
            return GetUserResponse.ok();
        } catch (RuntimeException ex) {
            return GetUserResponse.error(ex.getLocalizedMessage());
        }
    }

    /*
    @GetMapping("user_list")
    public UserListResponse userList() {
        List<User> users = userRepository.findAll();
        return new UserListResponse(users.stream().map(User::getUsername).collect(Collectors.toList()));
    }
    */
}
