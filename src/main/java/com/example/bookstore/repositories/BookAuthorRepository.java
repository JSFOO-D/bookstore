package com.example.bookstore.repositories;

import com.example.bookstore.entities.BookAuthor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookAuthorRepository extends CrudRepository<BookAuthor, Integer> {
    @Query("SELECT ba FROM BookAuthor ba WHERE ba.author.authorId = ?1 and ba.book.bookId = ?2")
    BookAuthor findByAuthorIdAndBookId(Integer authorId, Integer bookId);

    @Query("SELECT ba FROM BookAuthor ba WHERE ba.book.bookId = ?1")
    List<BookAuthor> findByBookId(Integer bookId);

    @Query("SELECT ba FROM BookAuthor ba WHERE ba.author.authorId = ?1")
    List<BookAuthor> findByAuthorId(Integer authorId);
}