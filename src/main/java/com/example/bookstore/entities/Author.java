package com.example.bookstore.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "author")
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="author_id", nullable = false)
    private Integer authorId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date birthday;

//    @OneToMany(mappedBy = "author", cascade=CascadeType.ALL)
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
    public Integer getAuthorId() {
        return authorId;
    }

    public Author setAuthorId(Integer authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Author setName(String name) {
        this.name = name;
        return this;
    }
    public Date getBirthday() {
        return birthday;
    }

    public Author setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

//    public Set<BookAuthor> getBookAuthors() {
//        return bookAuthors;
//    }
//
//    public Author setBookAuthor(Set<BookAuthor> bookAuthors) {
//        this.bookAuthors = bookAuthors;
//        return this;
//    }

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

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                ", birthday='" + birthday +
//                ", bookAuthors='" + bookAuthors +
                ", createdTime='" + createdTime + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
