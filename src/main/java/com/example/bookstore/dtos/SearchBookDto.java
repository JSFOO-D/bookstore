package com.example.bookstore.dtos;

public class SearchBookDto {
    private String title;
    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "SearchBookDto{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
