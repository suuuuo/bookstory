package com.elice.bookstore.book.domain.qna;

import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.qna.dto.QuestionRequest;

public class QuestionMapper {

    public static Question toEntity(QuestionRequest request, Book book) {
        if (request == null || book == null) {
            return null;
        }

        return Question.builder()
                .book(book)
                .content(request.getContent())
                .createdBy(request.getCreatedBy())
                .status(QuestionStatus.ANSWER_PENDING)
                .build();
    }
}

