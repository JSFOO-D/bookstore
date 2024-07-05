package com.example.bookstore.repositories;

import com.example.bookstore.entities.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {
    Optional<Author> findByName(String name);
    @Query("SELECT a FROM Book b, Author a, BookAuthor ba where a.authorId=ba.author.authorId and b.bookId=ba.book.bookId and b.isbn=?1")
    List<Author> searchByIsbn(String isbn);

}
