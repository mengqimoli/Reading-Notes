package com.example.ice.test.entity;

/**
 * Created by ice on 4/17/17.
 * @author ice
 */

public class Book {
    private String bookId;
    private String name;
    private String author;
    private String press;
    private String ISBN;
    private String classify;

    public Book(){
        bookId=null;name=null;author=null;press=null;ISBN=null;classify=null;
    }
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }
}
