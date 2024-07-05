package com.example.bookstore.service;

import com.example.bookstore.dtos.DeleteBookDto;
import com.example.bookstore.dtos.RegisterBookDto;
import com.example.bookstore.dtos.ResponseBookDto;
import com.example.bookstore.entities.Author;
import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.BookAuthor;
import com.example.bookstore.entities.User;
import com.example.bookstore.repositories.AuthorRepository;
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
import java.util.Timer;

@Service
@Slf4j
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookAuthorRepository bookAuthorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookRepository = bookRepository;
    }

    public List<Author> allAuthors() {
        // List all authors
        List<Author> authors = new ArrayList<Author>();

        authorRepository.findAll().forEach(authors::add);
        log.info("allAuthors.authors : " + authors);

        return authors;
    }

    public ResponseBookDto createAuthor(RegisterBookDto bookInput, ResponseBookDto responseBookDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        List<Author> authorsList = new ArrayList<Author>();
        Timestamp timestamp = Timestamp.from(Instant.now());
        for(Author author : bookInput.getAuthors()){
            // Check for existing author
            Optional<Author> retrievedAuthor = authorRepository.findByName(author.getName());
            log.info("createAuthor.retrievedAuthor : " + retrievedAuthor);
            if(!retrievedAuthor.isPresent()){
                // If author do not exist, create
                author.setCreatedBy(currentUser.getFullName());
                author.setUpdatedBy(currentUser.getFullName());
                author.setCreatedTime(timestamp);
                author.setUpdatedTime(timestamp);
                responseBookDto.setAuthorSaveType("CREATE");
                authorRepository.save(author);
            } else {
                // If author exist, do not create
                author.setAuthorId(retrievedAuthor.get().getAuthorId());
                author.setCreatedBy(retrievedAuthor.get().getCreatedBy());
                author.setUpdatedBy(retrievedAuthor.get().getUpdatedBy());
                author.setCreatedTime(retrievedAuthor.get().getCreatedTime());
                author.setUpdatedTime(retrievedAuthor.get().getUpdatedTime());
                responseBookDto.setAuthorSaveType("EXISTS");
            }
            authorsList.add(author);
        }
        // init authors
        responseBookDto.setAuthors(authorsList);
        // return response book
        log.info("createAuthor.responseBookDto : " + responseBookDto);
        return responseBookDto;
    }

    public List<Author> deleteAuthorByBook(DeleteBookDto deleteBookDto, List<BookAuthor> bookAuthors){
        List<Author> authors = new ArrayList<Author>();
        List<Author> deletedAuthors = new ArrayList<Author>();
        Book searchBook = new Book();
        // Find list of authors that were deleted through book authors but still belong to some books
        log.info("deleteAuthorByBook.deleteBookDto : " + deleteBookDto);
        log.info("deleteAuthorByBook.bookAuthors : " + bookAuthors);
        // check if they exists in bookAuthor if exist, do not delete
        for(BookAuthor bookAuthor:bookAuthors){
            List<BookAuthor> checkBookAuthor = new ArrayList<>();
            Optional<Author> author = Optional.of(new Author());
            checkBookAuthor = bookAuthorRepository.findByAuthorId(bookAuthor.getAuthor().getAuthorId());
            log.info("deleteAuthorByBook.checkBookAuthor : " + checkBookAuthor);
            if(checkBookAuthor.size()==0){
                author = authorRepository.findById(bookAuthor.getAuthor().getAuthorId());
                log.info("Delete of author table : " + author);
                authorRepository.deleteById(author.get().getAuthorId());
                deletedAuthors.add(author.get());
            } else {
                log.info("Skipping delete of author table as author belongs to another book too");
            }
        }
        log.info("deleteAuthorByBook.deletedAuthors : " + deletedAuthors);

        return deletedAuthors;
    }
}
