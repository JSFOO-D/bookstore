package com.example.bookstore.service;

import com.example.bookstore.dtos.*;
import com.example.bookstore.entities.Author;
import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.BookAuthor;
import com.example.bookstore.entities.User;
import com.example.bookstore.repositories.AuthorRepository;
import com.example.bookstore.repositories.BookAuthorRepository;
import com.example.bookstore.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookAuthorRepository bookAuthorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookAuthorRepository bookAuthorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookAuthorRepository = bookAuthorRepository;
    }

    public List<Book> allBooks() {
        // List all books
        List<Book> books = new ArrayList<>();

        bookRepository.findAll().forEach(books::add);
        log.info("Books : " + books);
        // Retrieve all authors of books
        for(Book book:books){
            List<Author> authors = new ArrayList<>();
            List<BookAuthor> bookAuthorList = bookAuthorRepository.findByBookId(book.getBookId());
            log.info("bookAuthorList : " + bookAuthorList);
            for(BookAuthor bookAuthor: bookAuthorList){
                Optional<Author> author = authorRepository.findById(bookAuthor.getAuthor().getAuthorId());
                if(author.isPresent()){
                    authors.add(author.get());
                }
            }
            book.setAuthors(authors);
        }



        return books;
    }

    public ResponseBookDto createBook(RegisterBookDto bookInput, ResponseBookDto responseBookDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        Book bookAdded = new Book();
        Timestamp timestamp = Timestamp.from(Instant.now());
        Book retrievedBook = bookRepository.findByIsbn(bookInput.getIsbn());
        log.info("updateBook.createBook : " + retrievedBook);
        // Check if book exists
        if(ObjectUtils.isEmpty(retrievedBook)){
            // if do not exist, create book
            bookAdded.setIsbn(bookInput.getIsbn());
            bookAdded.setTitle(bookInput.getTitle());
            bookAdded.setBookYear(bookInput.getBookYear());
            bookAdded.setPrice(bookInput.getPrice());
            bookAdded.setGenre(bookInput.getGenre());
            bookAdded.setCreatedBy(currentUser.getFullName());
            bookAdded.setUpdatedBy(currentUser.getFullName());
            bookAdded.setCreatedTime(timestamp);
            bookAdded.setUpdatedTime(timestamp);
            bookRepository.save(bookAdded);
            responseBookDto.setBookSaveType("CREATE");
            responseBookDto.setRemarks("CREATE BOOK");
        } else {
            // if exist, do not create book
            bookAdded.setBookId(retrievedBook.getBookId());
            bookAdded.setIsbn(retrievedBook.getIsbn());
            bookAdded.setTitle(retrievedBook.getTitle());
            bookAdded.setBookYear(retrievedBook.getBookYear());
            bookAdded.setPrice(retrievedBook.getPrice());
            bookAdded.setGenre(retrievedBook.getGenre());
            bookAdded.setCreatedBy(retrievedBook.getCreatedBy());
            bookAdded.setUpdatedBy(retrievedBook.getUpdatedBy());
            bookAdded.setCreatedTime(retrievedBook.getCreatedTime());
            bookAdded.setUpdatedTime(retrievedBook.getUpdatedTime());
            responseBookDto.setBookSaveType("EXISTS");
            responseBookDto.setRemarks("NOT CREATING BOOK");
        }
        responseBookDto.setBook(bookAdded);

        return responseBookDto;
    }

    public ResponseBookDto updateBook(RegisterBookDto bookInput, ResponseBookDto responseBookDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        Book bookUpdated = new Book();
        Timestamp timestamp = Timestamp.from(Instant.now());
        Book retrievedBook = bookRepository.findByIsbn(bookInput.getIsbn());
        log.info("updateBook.retrievedBook : " + retrievedBook);
        // check if book exists
        if(ObjectUtils.isEmpty(retrievedBook)){
            // if book do not exist do not do anything
            responseBookDto.setBookSaveType("NOT EXIST");
            responseBookDto.setRemarks("BOOK NOT FOUND");
        } else {
            // if book exists retrieve and update
            bookUpdated.setBookId(retrievedBook.getBookId());
            bookUpdated.setIsbn(retrievedBook.getIsbn());
            bookUpdated.setTitle(bookInput.getTitle());
            bookUpdated.setBookYear(bookInput.getBookYear());
            bookUpdated.setPrice(bookInput.getPrice());
            bookUpdated.setGenre(bookInput.getGenre());
            bookUpdated.setCreatedBy(retrievedBook.getCreatedBy());
            bookUpdated.setUpdatedBy(currentUser.getFullName());
            bookUpdated.setCreatedTime(retrievedBook.getCreatedTime());
            bookUpdated.setUpdatedTime(timestamp);
            responseBookDto.setBookSaveType("UPDATE");
            bookRepository.save(bookUpdated);
            responseBookDto.setRemarks("BOOK UPDATED");
        }
        responseBookDto.setBook(bookUpdated);

        return responseBookDto;
    }

    public ResponseSearchBookDto searchBooksByTitle(String title, ResponseSearchBookDto responseSearchBookDto) {
        // search book by title
        List<Book> retrievedBooks = bookRepository.findByTitle(title);
        log.info("searchBooksByTitle.retrievedBooks : " + retrievedBooks);
        if(retrievedBooks.size()==0){
            // if book search by title not found
            responseSearchBookDto.setRemarks("BOOK NOT FOUND");
        } else {
            // if book search by title found
            for(Book book:retrievedBooks){
                List<Author> authors = new ArrayList<>();
                List<BookAuthor> bookAuthorList = bookAuthorRepository.findByBookId(book.getBookId());
                log.info("bookAuthorList : " + bookAuthorList);
                for(BookAuthor bookAuthor: bookAuthorList){
                    Optional<Author> author = authorRepository.findById(bookAuthor.getAuthor().getAuthorId());
                    if(author.isPresent()){
                        authors.add(author.get());
                    }
                }
                book.setAuthors(authors);
            }
            responseSearchBookDto.setBooks(retrievedBooks);
            responseSearchBookDto.setRemarks("BOOK(s) FOUND");
        }

        return responseSearchBookDto;
    }

    public ResponseSearchBookDto searchBooksByAuthor(String authorName, ResponseSearchBookDto responseSearchBookDto) {
        // Search books by author name
        List<Book> booksFound = bookRepository.findByAuthorName(authorName);
        log.info("searchBooksByAuthor.booksFound : " + booksFound);
        if(booksFound.size()==0){
            // Search books by author name if not found
            responseSearchBookDto.setRemarks("BOOK NOT FOUND");
        } else {
            // Search books by author name if found, populate all authors by the book
            for(Book book:booksFound){
                List<Author> authors = new ArrayList<>();
                List<BookAuthor> bookAuthorList = bookAuthorRepository.findByBookId(book.getBookId());
                log.info("bookAuthorList : " + bookAuthorList);
                for(BookAuthor bookAuthor: bookAuthorList){
                    Optional<Author> author = authorRepository.findById(bookAuthor.getAuthor().getAuthorId());
                    if(author.isPresent()){
                        authors.add(author.get());
                    }
                }
                book.setAuthors(authors);
            }
        }

        responseSearchBookDto.setBooks(booksFound);

        return responseSearchBookDto;
    }

    public ResponseSearchBookDto searchBooksByTitleAndAuthor(SearchBookDto searchBookDto, ResponseSearchBookDto responseSearchBookDto) {
        // search by title and author name
        List<Book> booksFound = bookRepository.findByTitleAndAuthorName(searchBookDto.getTitle(), searchBookDto.getAuthor());
        log.info("searchBooksByTitleAndAuthor.booksFound : " + booksFound);
        if(booksFound.size()==0){
            // if search by title and author name, not found
            responseSearchBookDto.setRemarks("BOOK NOT FOUND");
        } else {
            // if search by title and author name, book found
            for(Book book:booksFound){
                List<Author> authors = new ArrayList<>();
                List<BookAuthor> bookAuthorList = bookAuthorRepository.findByBookId(book.getBookId());
                log.info("bookAuthorList : " + bookAuthorList);
                for(BookAuthor bookAuthor: bookAuthorList){
                    Optional<Author> author = authorRepository.findById(bookAuthor.getAuthor().getAuthorId());
                    if(author.isPresent()){
                        authors.add(author.get());
                    }
                }
                book.setAuthors(authors);
            }
            responseSearchBookDto.setRemarks("BOOK(s) FOUND");
            responseSearchBookDto.setBooks(booksFound);
        }
        return responseSearchBookDto;
    }

    public Book searchBookByIsbn(String isbn){
        Book searchBook = new Book();
        searchBook = bookRepository.findByIsbn(isbn);
        log.info("searchBookByIsbn.searchBook : " + searchBook);
        return searchBook;
    }
    public Book deleteBookByIsbn(String isbn){
        Book bookDeleted = new Book();
        bookDeleted = bookRepository.findByIsbn(isbn);
        bookRepository.deleteById(bookDeleted.getBookId());
        log.info("deleteBookByIsbn.bookDeleted : " + bookDeleted);

        return bookDeleted;
    }
}
