package org.ksushka.letyclone.services;

import org.ksushka.letyclone.AccountInfo;
import org.ksushka.letyclone.model.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    public AccountInfo getAccountInfo(Integer userId) {
        if (userId == null) throw new RuntimeException("UserId должен быть передан");
        User user = userRepository.findById(userId.intValue());
        if (user == null) throw new RuntimeException("Пользователя с данным userId не найдено");
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.username = user.getUsername();
        accountInfo.balance = user.getBalance();
        accountInfo.ghostBalance = 0.0;
        List<Item> items = itemRepository.findAllByUserAndCanBeRefundedAndRefunded(user, true, false);

        for (Item item : items) {
            accountInfo.ghostBalance += item.getCashback();
        }
        return accountInfo;
    }

    @Transactional
    public int login(String username, String password) {
        if (password == null || username == null) throw new RuntimeException("Имя пользователя и пароль должны быть заданы");
        if (username.isEmpty()) throw new RuntimeException("Имя пользователя не может быть пустым");
        if (password.isEmpty() || password.contains(" ")) throw new RuntimeException("Пароль не может быть пустым или содержать пробелы");

        try {
            User user = userRepository.findByUsername(username);
            if (user == null) throw new RuntimeException("Пользователя с таким username не существует");
            if (!user.getPassword().equals(password)) throw new RuntimeException("Неправильный пароль");
            return user.getId();

        } catch (NoResultException ex) {
            throw new RuntimeException("Пользователь не найден");
        }
    }
    
    @Transactional
    public int register(String username, String password) {
        if (username == null || password == null) throw new RuntimeException("Все поля должны быть заполнены");
        if (username.isEmpty()) throw new RuntimeException("Имя пользователя не может быть пустым");

        Pattern p = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]+$");
        Matcher m = p.matcher(username);
        if (!m.matches()) throw new RuntimeException("Указан некорректный почтовый адрес!");

        if (password.isEmpty() || password.contains(" ")) throw new RuntimeException("Пароль не может быть пустым или содержать пробелы");

        if (userRepository.existsByUsername(username)) throw new RuntimeException("Пользователь с таким логином уже существует");

        User user = new User(username, password, 0.0);
        userRepository.save(user);

        return user.getId();
    }

}
