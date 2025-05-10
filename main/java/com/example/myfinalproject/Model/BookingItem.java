package com.example.myfinalproject.Model;

public class BookingItem {
    private String bookingItemID;
    private String bookName;
    private String email;
    private String borrowDate;
    private String returnDate;
    private String status;

    public BookingItem() {
    }

    public BookingItem(String bookingItemID, String bookName, String email, String borrowDate, String returnDate, String status) {
        this.bookingItemID = bookingItemID;
        this.bookName = bookName;
        this.email = email;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public String getBookingItemID() {
        return bookingItemID;
    }

    public void setBookingItemID(String bookingItemID) {
        this.bookingItemID = bookingItemID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUserName() {
        return email;
    }

    public void setUserName(String userName) {
        this.email = userName;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookingItem{" +
                "bookingItemID='" + bookingItemID + '\'' +
                ", bookName='" + bookName + '\'' +
                ", email='" + email + '\'' +
                ", borrowDate='" + borrowDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
