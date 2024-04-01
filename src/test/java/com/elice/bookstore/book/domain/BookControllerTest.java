package com.elice.bookstore.book.domain;

import com.elice.bookstore.book.domain.dto.BookRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import javax.xml.transform.Result;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    public void mockMvcSetup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        bookRepository.deleteAll();
    }

    @DisplayName("Book 책 생성 성공")
    @Test
    public void saveBook() throws Exception{
        //given
        final String url = "/api/books";
        final String itemName = "itemName";
        final Integer price = 1000;
        final String author = "author";
        final String description = "description";
        final String publisher = "publisher";

        final BookRequest bookRequest = new BookRequest(itemName,price,author,description,publisher);

        // 객체 직렬화
        final String requestBody = objectMapper.writeValueAsString(bookRequest);

        //when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));
        //then
        result.andExpect(status().isCreated());

        List<Book> books = bookRepository.findAll();

        assertThat(books.size()).isEqualTo(1);
        assertThat(books.get(0).getItemName()).isEqualTo(itemName);
        assertThat(books.get(0).getPrice()).isEqualTo(price);
        assertThat(books.get(0).getAuthor()).isEqualTo(author);
        assertThat(books.get(0).getDescription()).isEqualTo(description);
    }
}