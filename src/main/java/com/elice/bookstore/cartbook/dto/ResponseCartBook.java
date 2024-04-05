package com.elice.bookstore.cartbook.dto;

public record ResponseCartBook (
    long id,
    long cartId,
    String itemName,
    int price,
    String imgPath,
    int count
){}
