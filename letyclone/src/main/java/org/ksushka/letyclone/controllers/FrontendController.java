package org.ksushka.letyclone.controllers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.ksushka.letyclone.messages.*;
import org.ksushka.letyclone.model.Shop;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FrontendController {
    @Value("${server.port}")
    private int port;

    @GetMapping("/")
    public String index(ModelMap m, HttpSession s) {
        Integer userId = (Integer) s.getAttribute("userId");
        RestTemplate rt = new RestTemplate();

        if (userId != null) {

            final String url = "http://localhost:" + port + "/api/get_info?userId={id}";
            GetAccountInfoResponse resp = rt.getForObject(url, GetAccountInfoResponse.class, userId);
            if (resp != null && resp.status.equals("ok")) {
                m.put("user_id", userId);
                m.put("username", resp.username);
                m.put("actualBalance", resp.actualBalance);
                m.put("ghostBalance", resp.ghostBalance);
            }
        }

        final String shopsUrl = "http://localhost:" + port + "/api/get_shops";

        GetShopsResponse resp1 = rt.getForObject(shopsUrl, GetShopsResponse.class);
        if (resp1 == null) m.put("shops", new ArrayList<Shop>());
        else m.put("shops", resp1.shops);

        return "index";
    }

    @PostMapping(value = "/login")
    public @ResponseBody RedirectView login(
            RedirectAttributes m, HttpSession s,
            @RequestParam String username,
            @RequestParam String password) {

        RestTemplate rt = new RestTemplate();
        final String url = "http://localhost:" + port + "/api/login";

        try {
            LoginResponse resp = rt.postForObject(url, new LoginRequest(username, password), LoginResponse.class);
            if (resp == null) m.addFlashAttribute("message", "Error response from");
            else {
                if (resp.status.equals("ok")) {
                    s.setAttribute("userId", resp.userId);
                } else {
                    System.out.println("Even here, what the hell?");
                    System.out.println(resp.error);
                    m.addFlashAttribute("message", resp.error);
                }
            }
        } catch (RestClientException ex) {
            m.addFlashAttribute("message", "Error accessing API");
        }
        return new RedirectView("/");
    }

    @GetMapping("/logout")
    public @ResponseBody RedirectView logout(HttpSession s) {
        s.removeAttribute("userId");
        return new RedirectView("/");
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody RedirectView postRegister(
            HttpSession s,
            RedirectAttributes m,
            @RequestParam String username,
            @RequestParam String password) {
        RestTemplate rt = new RestTemplate();
        final String url = "http://localhost:" + port + "/api/register";

        try {
            RegisterResponse resp = rt.postForObject(url, new RegisterRequest(username, password), RegisterResponse.class);
            if (resp == null) {
                m.addFlashAttribute("message", "Empty response from API");
            } else {
                if (resp.status.equals("ok")) {
                    s.setAttribute("userId", resp.userId);
                    m.addFlashAttribute("message", "You've been successfully registered");
                } else {
                    m.addFlashAttribute("message", resp.error);
                }
            }
        } catch (RestClientException ex) {
            m.addFlashAttribute("message", "Errors accessing API");
        }
        return new RedirectView("/");
    }

    @GetMapping(value = {"/balance", "balance.html"})
    public String balance(
            HttpSession s,
            ModelMap m) {
        Integer userId = (Integer) s.getAttribute("userId");
        RestTemplate rt = new RestTemplate();

        if (userId != null) {
            final String url = "http://localhost:" + port + "/api/get_info?userId={id}";

            GetAccountInfoResponse resp = rt.getForObject(url, GetAccountInfoResponse.class, userId);
            if (resp != null && resp.status.equals("ok")) {
                m.put("user_id", userId);
                m.put("username", resp.username);
                m.put("actualBalance", resp.actualBalance);
                m.put("ghostBalance", resp.ghostBalance);
            }
        }
        return "balance";
    }

    @PostMapping(value = "/withdraw", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody RedirectView withdraw(HttpSession s, ModelMap m) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = createHeaders("letyclone", "HtngTfwhbt8E1Xwawd1tJ72XrQqsDq");
        final String url = "http://localhost:" + port + "/api/private/withdraw";

        Integer userId = (Integer)s.getAttribute("userId");
        if (userId == null) return new RedirectView("balance");
        try {
            System.out.println(userId);
            GetUserResponse resp = rt.postForObject(
                    url,
                    new HttpEntity(new GetUserRequest(userId), headers),
                    GetUserResponse.class
            );

            if (resp == null) {
                m.put("message", "Empty response from API");
            } else {
                if (resp.status.equals("error")) {
                    m.put("message", resp.error);
                }
            }
        } catch (RestClientException ex) {
            m.put("message", "Errors accessing API");
            ex.printStackTrace();
        }
        return new RedirectView("balance");
    }

    HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

//    @GetMapping("/user_list")
//    public String userList(ModelMap m) {
//        RestTemplate rt = new RestTemplate();
//        final String url = "http://localhost:8080/api/user_list";
//        UserListResponse resp = rt.getForObject(url, UserListResponse.class);
//        m.put("usernames", resp.usernames);
//        return "user_list";
//    }
}
