package com.elice.bookstore.cartbook.dto;

public record RequestCartBook(
    long id,
    long bookId,
    String itemName,
    int price,
    String imgPath,
    int count
) {}