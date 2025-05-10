package com.example.myfinalproject.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String bookID;
    private String bookName;
    private String bookAuthor;
    private String bookCategory;
    private String bookPublishedDate;
    private String bookLocation;
    private int bookQuantity;
    private int bookBorrowedQuan;

    public Book() {
    }

    public Book(String bookID, String bookName) {
        this.bookID = bookID;
        this.bookName = bookName;
    }

    public Book(String bookName, String bookAuthor, String bookCategory, String bookPublishedDate, String bookLocation, int bookQuantity, int bookBorrowedQuan) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookCategory = bookCategory;
        this.bookPublishedDate = bookPublishedDate;
        this.bookLocation = bookLocation;
        this.bookQuantity = bookQuantity;
        this.bookBorrowedQuan = bookBorrowedQuan;
    }

    public Book(String bookID, String bookName, String bookAuthor, String bookCategory, String bookPublishedDate, String bookLocation, int bookQuantity, int bookBorrowedQuan) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookCategory = bookCategory;
        this.bookPublishedDate = bookPublishedDate;
        this.bookLocation = bookLocation;
        this.bookQuantity = bookQuantity;
        this.bookBorrowedQuan = bookBorrowedQuan;
    }

    public Book(String bookName, String bookAuthor, String bookCategory, String bookPublishedDate, int bookQuantity) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookCategory = bookCategory;
        this.bookPublishedDate = bookPublishedDate;
        this.bookQuantity = bookQuantity;
    }


    protected Book(Parcel in) {
        bookID = in.readString();
        bookName = in.readString();
        bookAuthor = in.readString();
        bookCategory = in.readString();
        bookPublishedDate = in.readString();
        bookLocation = in.readString();
        bookQuantity = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getBookPublishedDate() {
        return bookPublishedDate;
    }

    public void setBookPublishedDate(String bookPublishedDate) {
        this.bookPublishedDate = bookPublishedDate;
    }

    public String getBookLocation() {
        return bookLocation;
    }

    public void setBookLocation(String bookLocation) {
        this.bookLocation = bookLocation;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public int getBookBorrowedQuan() {
        return bookBorrowedQuan;
    }

    public void setBookBorrowedQuan(int bookBorrowedQuan) {
        this.bookBorrowedQuan = bookBorrowedQuan;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookCategory='" + bookCategory + '\'' +
                ", bookPublishedDate='" + bookPublishedDate + '\'' +
                ", bookLocation='" + bookLocation + '\'' +
                ", bookQuantity=" + bookQuantity +
                ", bookBorrowedQuan=" + bookBorrowedQuan +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookID);
        dest.writeString(bookName);
        dest.writeString(bookAuthor);
        dest.writeString(bookCategory);
        dest.writeString(bookPublishedDate);
        dest.writeString(bookLocation);
        dest.writeInt(bookQuantity);
    }

}
