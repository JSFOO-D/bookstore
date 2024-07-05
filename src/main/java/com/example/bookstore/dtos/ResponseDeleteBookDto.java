package com.example.bookstore.dtos;

import com.example.bookstore.entities.Author;
import com.example.bookstore.entities.Book;

import java.util.List;

public class ResponseDeleteBookDto {

    private Book book;
    private List<Author> authors;
    private String status;
    private String bookSaveType;
    private String authorSaveType;
    private String remarks;

    public Book getBook() {
        return book;
    }

    public ResponseDeleteBookDto setBook(Book book) {
        this.book = book;
        return this;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public ResponseDeleteBookDto setAuthors(List<Author> authors) {
        this.authors = authors;
        return this;
    }
    public String getStatus() {
        return status;
    }

    public ResponseDeleteBookDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getBookSaveType() {
        return bookSaveType;
    }

    public ResponseDeleteBookDto setBookSaveType(String bookSaveType) {
        this.bookSaveType = bookSaveType;
        return this;
    }

    public String getAuthorSaveType() {
        return authorSaveType;
    }

    public ResponseDeleteBookDto setAuthorSaveType(String authorSaveType) {
        this.authorSaveType = authorSaveType;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "ResponseBookDto{" +
                "Book='" + book + '\'' +
                ", Authors='" + authors + '\'' +
                ", status='" + status + '\'' +
                ", bookSaveType='" + bookSaveType + '\'' +
                ", authorSaveType='" + authorSaveType + '\'' +
                ", authorSaveType='" + remarks + '\'' +
                '}';
    }

}
