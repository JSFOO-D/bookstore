package com.example.bookstore.dtos;

import com.example.bookstore.entities.Author;

import java.util.Date;
import java.util.List;

public class RegisterBookDto {
    private String isbn;
    private String title;
    private List<Author> authors;
    private Integer bookYear;
    private Double price;
    private String genre;

    public String getIsbn() {
        return isbn;
    }

    public RegisterBookDto setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public RegisterBookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public RegisterBookDto setAuthors(List<Author> authors) {
        this.authors = authors;
        return this;
    }

    public Integer getBookYear() {
        return bookYear;
    }

    public RegisterBookDto setBookYear(Integer bookYear) {
        this.bookYear = bookYear;
        return this;
    }
    public Double getPrice() {
        return price;
    }

    public RegisterBookDto setYear(Double price) {
        this.price = price;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public RegisterBookDto setYear(String genre) {
        this.genre = genre;
        return this;
    }

    @Override
    public String toString() {
        return "RegisterUserDto{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", bookYear='" + bookYear + '\'' +
                ", price='" + price + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
