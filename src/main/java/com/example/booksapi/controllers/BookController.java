package com.example.booksapi.controllers;

import com.example.booksapi.dtos.BookRecordDto;
import com.example.booksapi.models.BookModel;
import com.example.booksapi.repositories.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    final
    BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping()
    public ResponseEntity<BookModel> saveBook(@RequestBody @Valid BookRecordDto bookRecordDto) {
        var bookModel = new BookModel();
        BeanUtils.copyProperties(bookRecordDto, bookModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookRepository.save(bookModel));
    }
}
