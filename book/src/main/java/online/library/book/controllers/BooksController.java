package online.library.book.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;
import online.library.book.dtos.CreateBookRequestDto;
import online.library.book.dtos.UpdateBookRequestDto;
import online.library.book.dtos.BookResponseDto;
import online.library.book.models.Book;
import online.library.book.services.BookService;
import online.library.book.utils.MapperConverter;
import online.library.book.utils.ValidationErrors;

@RestController
@RequestMapping("api/v1/books")
public class BooksController {

    @Autowired
    private BookService _bookService;

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        try {
            var books = _bookService.getAll();
            var booksDto = books.stream()
                    .map(book -> MapperConverter.convertToDto(book, BookResponseDto.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(booksDto);
        } catch (Exception e) {
            throw e;
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        try {
            return _bookService.getById(id)
                    .map(book -> ResponseEntity.ok(MapperConverter.convertToDto(book, BookResponseDto.class)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> createBook(@Valid @RequestBody CreateBookRequestDto bookRequestDto,
            BindingResult validateFields) {

        try {
            ValidationErrors.validateBindingResult(validateFields);
            var book = MapperConverter.convertToEntity(bookRequestDto, Book.class);
            var savedBook = _bookService.create(book);
            var bookResponseDto = MapperConverter.convertToDto(savedBook, BookResponseDto.class);

            return new ResponseEntity<>(bookResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long id,
            @Valid @RequestBody UpdateBookRequestDto bookRequestDto, BindingResult validateFields) {

        try {
            ValidationErrors.validateBindingResult(validateFields);
            var book = MapperConverter.convertToEntity(bookRequestDto, Book.class);
            var updatedBook = _bookService.update(id, book);
            var bookResponseDto = MapperConverter.convertToDto(updatedBook, BookResponseDto.class);

            return ResponseEntity.ok(bookResponseDto);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            _bookService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw e;
        }
    }
}
