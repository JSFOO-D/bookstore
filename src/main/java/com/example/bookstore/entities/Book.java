package com.example.bookstore.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "book")
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="book_id", nullable = false)
    private Integer bookId;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private Integer bookYear;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String genre;


//    @OneToMany(mappedBy = "book", cascade=CascadeType.ALL)
//    Set<BookAuthor> bookAuthors;


    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdTime;

    @Column
    private String createdBy;

    @Column
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedTime;

    @Column
    private String updatedBy;

    @OneToMany(fetch=FetchType.EAGER)
    private List<Author> authors;

    public Integer getBookId() {
        return bookId;
    }

    public Book setBookId(Integer bookId) {
        this.bookId = bookId;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public Book setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getBookYear() {
        return bookYear;
    }

    public Book setBookYear(Integer bookYear) {
        this.bookYear = bookYear;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Book setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public Book setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

//    public Set<BookAuthor> getBookAuthors() {
//        return bookAuthors;
//    }
//
//    public void setBookAuthors(Set<BookAuthor> bookAuthors) {
//        this.bookAuthors = bookAuthors;
//    }
    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId + '\'' +
                "isbn=" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", bookYear='" + bookYear + '\'' +
                ", price='" + price + '\'' +
                ", genre='" + genre + '\'' +
//                ", bookAuthors='" + bookAuthors + '\'' +
                ", authors='" + authors + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
