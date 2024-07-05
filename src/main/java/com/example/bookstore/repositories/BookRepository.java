package com.example.bookstore.repositories;

import com.example.bookstore.entities.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    @Query("SELECT b FROM Book b where b.title = ?1")
    List<Book> findByTitle(String title);

    @Query("SELECT b FROM Book b, Author a, BookAuthor ba where a.authorId=ba.author.authorId and b.bookId=ba.book.bookId and a.name = ?1")
    List<Book> findByAuthorName(String author);

    @Query("SELECT b FROM Book b, Author a, BookAuthor ba where a.authorId=ba.author.authorId and b.bookId=ba.book.bookId and b.title = ?1 and a.name = ?2")
    List<Book> findByTitleAndAuthorName(String title, String authorName);

    Book findByIsbn(String isbn);

}