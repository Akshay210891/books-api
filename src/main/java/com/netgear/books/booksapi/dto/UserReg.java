package com.netgear.books.booksapi.dto;

import lombok.AllArgsConstructor;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserReg {
    private String username;
    private String password;
    private String email;
}
