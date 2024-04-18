package com.elice.bookstore.cartbook.dto;

public record ResponseCartBook(
    long id,
    long cartId,
    long bookId,
    String itemName,
    int price,
    String imgPath,
    int count,
    int stock,
    String isbn) {}
