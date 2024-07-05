package com.example.bookstore.dtos;

import com.example.bookstore.entities.Author;
import com.example.bookstore.entities.Book;

import java.util.List;

public class ResponseSearchBookDto {

    private List<Book> books;
    private List<Author> authors;
    private String status;
    private String bookSaveType;
    private String authorSaveType;
    private String remarks;

    public List<Book> getBooks() {
        return books;
    }

    public ResponseSearchBookDto setBooks(List<Book> books) {
        this.books = books;
        return this;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public ResponseSearchBookDto setAuthors(List<Author> authors) {
        this.authors = authors;
        return this;
    }
    public String getStatus() {
        return status;
    }

    public ResponseSearchBookDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getBookSaveType() {
        return bookSaveType;
    }

    public ResponseSearchBookDto setBookSaveType(String bookSaveType) {
        this.bookSaveType = bookSaveType;
        return this;
    }

    public String getAuthorSaveType() {
        return authorSaveType;
    }

    public ResponseSearchBookDto setAuthorSaveType(String authorSaveType) {
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
        return "ResponseSearchBookDto{" +
                "Books='" + books + '\'' +
                ", Authors='" + authors + '\'' +
                ", status='" + status + '\'' +
                ", bookSaveType='" + bookSaveType + '\'' +
                ", authorSaveType='" + authorSaveType + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }

}
