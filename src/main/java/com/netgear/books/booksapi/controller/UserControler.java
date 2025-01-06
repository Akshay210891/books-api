package com.netgear.books.booksapi.controller;

import com.netgear.books.booksapi.dto.UserAuth;
import com.netgear.books.booksapi.dto.UserReg;
import com.netgear.books.booksapi.entity.User;
import com.netgear.books.booksapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserControler {

    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<String> authenticate(@RequestBody UserAuth authDetails) {
        return ResponseEntity.ok(userService.authenticate(authDetails));
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserReg regDetails) {
        return ResponseEntity.ok(userService.register(regDetails));
    }
}