package com.elice.bookstore.book.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ApiResponse {
    private Data data;

    @Getter
    @NoArgsConstructor
    public static class Data {
        private List<BookDetail> bestSeller;
    }

    @Getter
    @NoArgsConstructor
    public static class BookDetail {
        private String cmdtName; // 책 제목
        private String chrcName; // 저자 이름
        private String pbcmName; // 출판사
        private String inbukCntt; // 설명
        private String price; // 가격

    }
}
