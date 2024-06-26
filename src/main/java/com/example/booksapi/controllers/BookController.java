package com.example.booksapi.controllers;

import com.example.booksapi.dtos.BookRecordDto;
import com.example.booksapi.models.BookModel;
import com.example.booksapi.repositories.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/books")
public class BookController {
    final
    BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping()
    public ResponseEntity<List<BookModel>> getAllBooks() {
        List<BookModel> booksList = bookRepository.findAll();
        if (!booksList.isEmpty()) {
            for (BookModel book: booksList) {
                book.add(linkTo(methodOn(BookController.class).getOneBook(book.getId())).withSelfRel());
            }
        }
        return ResponseEntity.ok(booksList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneBook(@PathVariable UUID id) {
        Optional<BookModel> book = bookRepository.findById(id);
        return book.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.") : ResponseEntity.ok(book.get().add(linkTo(methodOn(BookController.class).getAllBooks()).withRel("All books")));
    }

    @PostMapping()
    public ResponseEntity<BookModel> saveBook(@RequestBody @Valid BookRecordDto bookRecordDto) {
        var bookModel = new BookModel();
        BeanUtils.copyProperties(bookRecordDto, bookModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookRepository.save(bookModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable UUID id, @RequestBody @Valid BookRecordDto bookRecordDto) {
        Optional<BookModel> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }
        var bookModel = book.get();
        BeanUtils.copyProperties(bookRecordDto, bookModel);
        return ResponseEntity.ok(bookRepository.save(bookModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable UUID id) {
        Optional<BookModel> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }
        bookRepository.delete(book.get());
        return ResponseEntity.status(HttpStatus.OK).body("Book deleted.");
    }
}
