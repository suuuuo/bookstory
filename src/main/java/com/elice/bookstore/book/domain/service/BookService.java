package com.elice.bookstore.book.domain.service;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.dto.ApiResponse;
import com.elice.bookstore.book.domain.dto.BookDetailResponse;
import com.elice.bookstore.book.domain.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final WebClient webClient;

    public BookService(BookRepository bookRepository, WebClient.Builder webClientBuilder) {
        this.bookRepository = bookRepository;
        this.webClient = webClientBuilder.baseUrl("https://product.kyobobook.co.kr").build();
    }

    public Flux<BookDetailResponse> fetchBestSellers() {
        return webClient.get()
                .uri("/api/gw/pub/pdt/best-seller/total?page=1&per=20&period=002&bsslBksClstCode=A")
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .flatMapMany(response -> Flux.fromIterable(response.getData().getBestSeller()))
                .map(detail -> new BookDetailResponse(detail.getCmdtName(), detail.getChrcName(), detail.getPbcmName(),detail.getPrice(),detail.getInbukCntt()))
                .onErrorResume(e -> Flux.empty()); // 에러 발생 시 빈 Flux 반환
    }

    public Flux<Book> fetchAndSaveBestSellers(){
        return fetchBestSellers()
                .map(bookDetailResponse -> {
                    Book book = Book.builder()
                            .itemName(bookDetailResponse.getItemName())
                            .author(bookDetailResponse.getAuthor())
                            .publisher(bookDetailResponse.getPublisher())
                            .price(new BigDecimal(bookDetailResponse.getPrice()))
                            .description(bookDetailResponse.getDescription())
                            .build();

                    return bookRepository.save(book);
                });
    }


}