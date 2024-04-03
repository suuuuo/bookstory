package com.elice.bookstore.book.domain.qna.dto;


import com.elice.bookstore.book.domain.Book;
import com.elice.bookstore.book.domain.qna.Question;
import com.elice.bookstore.book.domain.qna.QuestionStatus;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class QuestionRequest {
    private Long bookId;
    private String content;
    private String createdBy;


    public Question toEntity(Book book) {
        return Question.builder()
                .book(book)
                .content(content)
                .createdBy(createdBy)
                .status(QuestionStatus.ANSWER_PENDING) // 질문의 기본 상태도 설정할 수 있습니다.
                .build();
    }
}
