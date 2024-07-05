package com.example.bookstore.dtos;

public class DeleteBookDto {
    private String isbn;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "DeleteBookDto{" +
                "isbn='" + isbn + '\'' +
                '}';
    }
}
