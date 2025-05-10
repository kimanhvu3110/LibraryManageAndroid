package com.example.myfinalproject.Model;

public class FavBook {
    private String bookName;
    private String mail;

    public FavBook(String bookID, String userName) {
        this.bookName = bookID;
        this.mail = userName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "FavBook{" +
                "bookID=" + bookName +
                ", userName='" + mail + '\'' +
                '}';
    }
}
