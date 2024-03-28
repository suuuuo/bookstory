package com.elice.bookstore.book.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BookService {

    private final WebClient webClient;

    public BookService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://product.kyobobook.co.kr").build();
    }

    public Flux<BookDTO> fetchBestSellers() {
        return webClient.get()
                .uri("/api/gw/pub/pdt/best-seller/total?page=1&per=20&period=002&bsslBksClstCode=A")
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .flatMapMany(response -> Flux.fromIterable(response.getData().getBestSeller()))
                .map(detail -> new BookDTO(detail.getCmdtName(), detail.getChrcName(), detail.getPbcmName()))
                .onErrorResume(e -> Flux.empty()); // 에러 발생 시 빈 Flux 반환
    }

}