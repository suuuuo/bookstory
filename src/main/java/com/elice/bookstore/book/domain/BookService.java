package com.elice.bookstore.book.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    //create 데이터를 직접 넣을건인가 ? 외부에서 가져 올 것인가?

    //read
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findById(Long id){
        return bookRepository.findById(id).orElse(null);
    }
    //update



    //delete
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
}
