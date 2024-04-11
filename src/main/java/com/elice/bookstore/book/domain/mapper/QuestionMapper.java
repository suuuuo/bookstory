package com.elice.bookstore.book.domain.mapper;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.qna.Question;
import com.elice.bookstore.book.domain.qna.QuestionStatus;
import com.elice.bookstore.book.domain.dto.RequestQuestion;
import com.elice.bookstore.user.domain.User;

public class QuestionMapper {

    public static Question toEntity(RequestQuestion request, Book book, User user) {
        if (request == null || book == null || user == null) {
            return null;
        }

        return Question.builder()
                .book(book)
                .content(request.getContent())
                .createdBy(user.getId())
                .status(QuestionStatus.ANSWER_PENDING)
                .build();
    }

    public static Question toFindEntity(RequestQuestion request, Book book){
        if(request == null || book == null){
            return null;
        }
        return Question.builder()
                .book(book)
                .content(request.getContent())
                .createdBy(null)
                .status(QuestionStatus.ANSWER_PENDING)
                .build();
    }


}

