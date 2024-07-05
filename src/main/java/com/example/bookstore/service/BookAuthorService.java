package com.example.bookstore.service;

import com.example.bookstore.dtos.DeleteBookDto;
import com.example.bookstore.dtos.RegisterBookDto;
import com.example.bookstore.dtos.ResponseBookDto;
import com.example.bookstore.entities.Author;
import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.BookAuthor;
import com.example.bookstore.entities.User;
import com.example.bookstore.repositories.BookAuthorRepository;
import com.example.bookstore.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookAuthorService {
    private final BookAuthorRepository bookAuthorRepository;
    private final BookRepository bookRepository;

    public BookAuthorService(BookAuthorRepository bookAuthorRepository, BookRepository bookRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookRepository = bookRepository;
    }

    public List<BookAuthor> allBookAuthor() {
        // List all book authors
        List<BookAuthor> bookAuthors = new ArrayList<BookAuthor>();

        bookAuthorRepository.findAll().forEach(bookAuthors::add);
        log.info("allBookAuthor.bookAuthors : " + bookAuthors);

        return bookAuthors;
    }

    public List<BookAuthor> createBookAuthor(ResponseBookDto responseBookDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        List<BookAuthor> bookAuthorAdded = new ArrayList<BookAuthor>();
        Timestamp timestamp = Timestamp.from(Instant.now());

        for(Author author : responseBookDto.getAuthors()){
            BookAuthor bookAuthor = new BookAuthor();
            // Retrieve book Author record
            BookAuthor retrievedBookAuthor = bookAuthorRepository.findByAuthorIdAndBookId(responseBookDto.getBook().getBookId(), author.getAuthorId());
            log.info("createBookAuthor.retrievedBookAuthor : " + retrievedBookAuthor);
            if(retrievedBookAuthor!=null){
                // if exists skip
                responseBookDto.setRemarks("Book Author exists");
                log.info("createBookAuthor exists skip adding");
            } else {
                // if do not exists create
                bookAuthor.setAuthor(author);
                bookAuthor.setBook(responseBookDto.getBook());
                bookAuthor.setCreatedBy(currentUser.getFullName());
                bookAuthor.setUpdatedBy(currentUser.getFullName());
                bookAuthor.setCreatedTime(timestamp);
                bookAuthor.setUpdatedTime(timestamp);
                bookAuthorRepository.save(bookAuthor);
                bookAuthorAdded.add(bookAuthor);
            }
        }

        return bookAuthorAdded;
    }

    public List<BookAuthor> deleteBookAuthorByBook(DeleteBookDto deleteBookDto) {
        List<BookAuthor> bookAuthors = new ArrayList<BookAuthor>();
        List<BookAuthor> deletedBookAuthors = new ArrayList<BookAuthor>();
        Book deleteBook = bookRepository.findByIsbn(deleteBookDto.getIsbn());
        log.info("deleteBookAuthorByBook.deleteBook : " + deleteBook);
        bookAuthors = bookAuthorRepository.findByBookId(deleteBook.getBookId());
        log.info("deleteBookAuthorByBook.bookAuthors : " + bookAuthors);
        for(BookAuthor bookAuthor:bookAuthors){
            log.info("Deleting book authors record : " + bookAuthor);
            deletedBookAuthors.add(bookAuthor);
            bookAuthorRepository.deleteById(bookAuthor.getId());
        }

        return deletedBookAuthors;
    }
}
