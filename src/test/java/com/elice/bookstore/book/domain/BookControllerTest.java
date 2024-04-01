package com.elice.bookstore.book.domain;

import com.elice.bookstore.book.domain.dto.BookRequest;
import com.elice.bookstore.book.domain.dto.UpdateBookRequest;
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

    @DisplayName("Book 책 수정 성공")
    @Test
    public void updateBook() throws Exception{
        //given
        final String url = "/api/books/{id}";
        final String itemName = "itemName";
        final Integer price = 1000;
        final String author = "author";
        final String publisher = "publisher";
        final String description = "description";

        Book saveBook = bookRepository.save(Book.builder()
            .itemName(itemName)
            .price(price)
            .author(author)
            .publisher(publisher)
            .description(description)
            .build());

        final String newItemName = "new itemName";
        final Integer newPrice = 1000;
        final String newAuthor = "new author";
        final String newPublisher = "new publisher";
        final String newDescription = "new description";

        UpdateBookRequest request = new UpdateBookRequest(newItemName,newPrice,newAuthor,newDescription,newPublisher);

        //when
        ResultActions resultActions = mockMvc.perform(put(url,saveBook.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)));

        //then
        resultActions.andExpect(status().isOk());

        Book book = bookRepository.findById(saveBook.getId()).get();

        assertThat(book.getItemName()).isEqualTo(newItemName);
        assertThat(book.getPrice()).isEqualTo(newPrice);
        assertThat(book.getAuthor()).isEqualTo(newAuthor);
        assertThat(book.getPublisher()).isEqualTo(newPublisher);
        assertThat(book.getDescription()).isEqualTo(newDescription);


    }

    @DisplayName("Book 블로그 글 조회 성공")
    @Test
    public void findAllBook() throws Exception{
        //given
        final String url = "/api/books";
        final String itemName = "itemName";
        final Integer price = 1000;
        final String author = "author";
        final String publisher = "publisher";

        bookRepository.save(Book.builder()
                .itemName(itemName)
                .price(price)
                .author(author)
                .publisher(publisher)
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemName").value(itemName))
                .andExpect(jsonPath("$[0].price").value(price))
                .andExpect(jsonPath("$[0].author").value(author))
                .andExpect(jsonPath("$[0].publisher").value(publisher));

    }


    @DisplayName("특정 책 조회 성공")
    @Test
    public void findByIdBooks() throws Exception{
        //given
        final String url = "/api/books/{id}";
        final String itemName = "itemName";
        final Integer price = 1000;
        final String author = "author";
        final String publisher = "publisher";

        Book book = bookRepository.save(Book.builder()
                .itemName(itemName)
                .price(price)
                .author(author)
                .publisher(publisher)
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url, book.getId()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName").value(itemName))
                .andExpect(jsonPath("$.price").value(price))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(jsonPath("$.publisher").value(publisher));
    }


    @DisplayName("책 삭제 성공")
    @Test
    public void deleteBook() throws Exception{
        //given

        final String url = "/api/books/{id}";
        final String itemName = "itemName";
        final Integer price = 1000;
        final String author = "author";
        final String publisher = "publisher";

        Book book = bookRepository.save(Book.builder()
                .itemName(itemName)
                .price(price)
                .author(author)
                .publisher(publisher)
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(delete(url, book.getId()))
                .andExpect(status().isOk());
                ;

        //then
        List<Book> books = bookRepository.findAll();
        assertThat(books).isEmpty();
    }
}