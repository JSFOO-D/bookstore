package com.example.bookstore.controllers;

import com.example.bookstore.dtos.*;
import com.example.bookstore.entities.Author;
import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.BookAuthor;
import com.example.bookstore.entities.User;
import com.example.bookstore.service.AuthorService;
import com.example.bookstore.service.BookAuthorService;
import com.example.bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RequestMapping("/books")
@RestController
@Slf4j
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookAuthorService bookAuthorService;

    public BookController(BookService bookService, AuthorService authorService, BookAuthorService bookAuthorService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookAuthorService = bookAuthorService;
    }

    // only allow authenticated to fire createBook api
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBookDto> createBook(@RequestBody RegisterBookDto registerBookDto) {
        log.info("Entering createBook api");
        log.info("registerBookDto : " + registerBookDto);
        ResponseBookDto responseBookDto = new ResponseBookDto();
        responseBookDto = bookService.createBook(registerBookDto, responseBookDto);
        if("EXISTS".equalsIgnoreCase(responseBookDto.getBookSaveType())){
            log.info("Skip adding author/bookAuthors because book exists");
        } else {
            responseBookDto = authorService.createAuthor(registerBookDto, responseBookDto);
            List<BookAuthor> bookAuthors = bookAuthorService.createBookAuthor(responseBookDto);
        }
        responseBookDto.setStatus("Success");
        return ResponseEntity.ok(responseBookDto);
    }

    // only allow authenticated to fire updateBook api
    @PostMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBookDto> updateBook(@RequestBody RegisterBookDto registerBookDto) {
        log.info("Entering updateBook api");
        log.info("registerBookDto : " + registerBookDto);
        ResponseBookDto responseBookDto = new ResponseBookDto();
        responseBookDto = bookService.updateBook(registerBookDto, responseBookDto);
        responseBookDto.setStatus("Success");
        return ResponseEntity.ok(responseBookDto);
    }

    // only allow authenticated to fire searchBook api
    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseSearchBookDto> searchBook(@RequestBody SearchBookDto searchBookDto) {
        log.info("Entering searchBook api");
        log.info("searchBookDto : " + searchBookDto);
        ResponseSearchBookDto responseSearchBookDto = new ResponseSearchBookDto();
        if(searchBookDto.getTitle().isEmpty() && searchBookDto.getAuthor().isEmpty()){
            responseSearchBookDto.setRemarks("Please key in title or/and author to search");
        } else if (!searchBookDto.getTitle().isEmpty() && searchBookDto.getAuthor().isEmpty()){
            log.info("Searching books by title : " + searchBookDto.getTitle());
            responseSearchBookDto.setRemarks("Searched by book title");
            responseSearchBookDto = bookService.searchBooksByTitle(searchBookDto.getTitle(), responseSearchBookDto);
        } else if (searchBookDto.getTitle().isEmpty() && !searchBookDto.getAuthor().isEmpty()){
            responseSearchBookDto.setRemarks("Searched by author name");
            log.info("Searching books by author : " + searchBookDto.getAuthor());
            responseSearchBookDto = bookService.searchBooksByAuthor(searchBookDto.getAuthor(), responseSearchBookDto);
        } else {
            responseSearchBookDto.setRemarks("Searched by book title and author name");
            log.info("Searching books by title : " + searchBookDto.getTitle() + " and author : " + searchBookDto.getAuthor());
            responseSearchBookDto = bookService.searchBooksByTitleAndAuthor(searchBookDto, responseSearchBookDto);

        }

        return ResponseEntity.ok(responseSearchBookDto);
    }

    // only allow super admin role to fire deleteBook api
    @PostMapping("/delete")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ResponseDeleteBookDto> deleteBook(@RequestBody DeleteBookDto deleteBookDto) {
        log.info("Entering deleteBook api");
        log.info("deleteBookDto : " + deleteBookDto);
        ResponseDeleteBookDto responseDeleteBookDto = new ResponseDeleteBookDto();
        List<BookAuthor> bookAuthorsDeleted = new ArrayList<BookAuthor>();
        List<Author> authorsDeleted = new ArrayList<Author>();
        Book bookDeleted = new Book();
        Book checkIfBookExist = new Book();
        // check if isbn exists else do not do any delete
        checkIfBookExist = bookService.searchBookByIsbn(deleteBookDto.getIsbn());
        if(ObjectUtils.isEmpty(checkIfBookExist)){
            responseDeleteBookDto.setRemarks("This book do not exist will skip delete");
        } else {
            bookAuthorsDeleted = bookAuthorService.deleteBookAuthorByBook(deleteBookDto);
            log.info("deleteBook : bookAuthorsDeleted : " + bookAuthorsDeleted);
            authorsDeleted = authorService.deleteAuthorByBook(deleteBookDto, bookAuthorsDeleted);
            log.info("deleteBook : authorsDeleted : " + authorsDeleted);
            responseDeleteBookDto.setAuthors(authorsDeleted);
            bookDeleted = bookService.deleteBookByIsbn(deleteBookDto.getIsbn());
            log.info("deleteBook : bookDeleted : " + bookDeleted);
            responseDeleteBookDto.setBook(bookDeleted);
            responseDeleteBookDto.setBookSaveType("DELETE");
            responseDeleteBookDto.setAuthorSaveType("DELETE");
            responseDeleteBookDto.setStatus("Success");
            responseDeleteBookDto.setRemarks("Deleted successful");
        }

        return ResponseEntity.ok(responseDeleteBookDto);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Book>> allBooks() {
        log.info("Entering allBooks api");
        List<Book> books = bookService.allBooks();

        return ResponseEntity.ok(books);
    }
}
