package com.example.biblioteca.model.book;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private String description;
    private int avaliableCopies;

    public int getId() {
        return id;
    }

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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAvaliableCopies() {
        return avaliableCopies;
    }

    public void setAvaliableCopies(int availableCopies) {
        this.avaliableCopies = availableCopies;
    }
}
