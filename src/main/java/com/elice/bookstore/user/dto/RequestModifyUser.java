package com.elice.bookstore.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record RequestModifyUser(
    Long id,
    String userName,
    String dateOfBirth,
    @Email(regexp = ".+@.+\\..+",
        message = "Email address is not formatted correctly. (ex: user@gmail.com)")
    String email,
    @Pattern(regexp = "010-(\\d{3,4})-\\d{4}",
        message = "Phone number is not formatted correctly. (ex: 010-1234-5678)")
    String phoneNumber,
    String address,
    Long point,
    String role) {
}