package com.elice.bookstore.cartbook.dto;

public record RequestCartBook(
    long id,
    long bookId,
    int count
) {}