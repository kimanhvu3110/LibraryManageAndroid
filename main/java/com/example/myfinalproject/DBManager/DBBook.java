package com.example.myfinalproject.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myfinalproject.Model.Account;
import com.example.myfinalproject.Model.Book;

import java.util.ArrayList;

public class DBBook extends SQLiteOpenHelper {
    ArrayList<Book> bookArrayList = new ArrayList<>();
    ArrayList<Account> accountArrayList = new ArrayList<>();


    public static final String DBNAME = "Project2.db";
    public static final String TABLE_BOOK = "BOOKS";
    public static final String TABLE_USERS = "USERS";
    public static final String TABLE_FAV_BOOK = "FAV_BOOK";

    SQLiteDatabase MyDB;

    public static final int VER = 1;
    public DBBook(@Nullable Context context) {
        super(context, DBNAME, null, VER);
    }

    private static final String query = "CREATE TABLE " + TABLE_BOOK +
            "(bookID integer primary key AUTOINCREMENT, " +
            "bookName text, " +
            "bookAuthor text, " +
            "bookCategory text, " +
            "bookPublishedDate text, " +
            "bookQuantity integer)";

    private static final String UserRoleQuery = "CREATE TABLE " + TABLE_USERS +
            "(userName text primary key, " +
            "passWord text, " +
            "phone text, " +
            "email text, role text)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("=======On create");
        db.execSQL(query);
        db.execSQL(UserRoleQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if Exists "+ TABLE_BOOK + "");
        db.execSQL("Drop table if Exists "+ TABLE_USERS + "");
        onCreate(db);
    }


    //Book DB management
//OPEN DB
    public void openDB() {
        try {
            MyDB = this.getWritableDatabase();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //CLOSE
    public void closeDB() {
        try {
            this.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Book SELECT query
    public ArrayList<Book> selectBook(){
        openDB();
        Cursor cursor = MyDB.rawQuery("select * from "+ TABLE_BOOK +"",null);;
        System.out.println("==============selectBook==============");
        while (cursor.moveToNext()){
            String bookID = cursor.getString(0);
            String bookName = cursor.getString(1);
            String bookAuthor = cursor.getString(2);
            String bookCategory = cursor.getString(3);
            String bookPublishedDate = cursor.getString(4);
            String bookLocation = cursor.getString(5);
            int bookQuantity = cursor.getInt(6);
            int bookBorrowedQuan = cursor.getInt(7);
            Book book = new Book(bookID,bookName,bookAuthor,bookCategory,bookPublishedDate,
                    bookLocation,bookQuantity,bookBorrowedQuan);
            bookArrayList.add(book);
        }
        System.out.println("==============bookArrayList===============");
        System.out.println(bookArrayList);
        closeDB();
        return bookArrayList;
    }

    public String addFavBook(String bookID, String userName){
        String result;
        ContentValues cv = new ContentValues();
        cv.put("bookID",bookID);
        cv.put("userName", userName);

        System.out.println("=========== addFavBook ============");
        System.out.println("userName: "+userName);
        System.out.println("bookID: "+bookID);

        MyDB = this.getWritableDatabase();
        //insert
        long insert = MyDB.insert(TABLE_FAV_BOOK, null, cv);
        if (insert != -1) {
            result = "Successfully inserted data";
        } else {
            result = "Book is already liked";
        }
        return result;
    }

    public String deleteFavBook(int bookID, String userName){
        int delete = MyDB.delete(TABLE_FAV_BOOK, "bookID = ? and userName = ?",
                new String[]{Integer.toString(bookID), userName});
        String result;
        if (delete == 1){
            result = "Successfully unlike";
        } else {
            result = "Something is wrong";
        }
        return result;
    }

    public ArrayList<Book> selectFavBook(String userName){
        openDB();
        String query = "SELECT *\n" +
                        "FROM BOOKS b\n" +
                        "INNER JOIN FAV_BOOK fb on b.bookID = fb.bookID\n" +
                        "Where fb.userName = ?";
        Cursor cursor = MyDB.rawQuery(query, new String[]{userName});
        System.out.println("----------selectBook----------");
        while (cursor.moveToNext()){
            String bookID = cursor.getString(0);
            String bookName = cursor.getString(1);
            String bookAuthor = cursor.getString(2);
            String bookCategory = cursor.getString(3);
            String bookPublishedDate = cursor.getString(4);
            String bookLocation = cursor.getString(5);
            int bookQuantity = cursor.getInt(6);
            int bookBorrowedQuan = cursor.getInt(7);
            Book book = new Book(bookID,bookName,bookAuthor,bookCategory,bookPublishedDate,
                    bookLocation,bookQuantity,bookBorrowedQuan);
            bookArrayList.add(book);
        }
        System.out.println("=====================================bookArrayList=============================================");
        System.out.println(bookArrayList);
        closeDB();
        return bookArrayList;
    }

    //Searching
    public Cursor searchByBook(String searchTerm) {
        System.out.println("----------retrieve----------");
        String[] columns = {"bookID", "bookName", "bookName", "bookAuthor", "bookCategory", "bookPublishedDate",
                "bookLocation", "bookQuantity", "bookBorrowedQuan"};
        Cursor c;
        if(searchTerm != null && searchTerm.length()>0) {
            String sql = "SELECT * FROM "+ TABLE_BOOK +" WHERE "
                    + "bookName "+" LIKE '%"+searchTerm+"%'";
            c = MyDB.rawQuery(sql,null);
            return c;
        }
        c = MyDB.query(TABLE_BOOK, columns,null,null,null,null,null);
        return c;
    }
    public Cursor searchByAuthor(String searchTerm) {
        System.out.println("----------retrieve----------");
        String[] columns = {"bookID", "bookName", "bookName", "bookAuthor", "bookCategory", "bookPublishedDate",
                "bookLocation", "bookQuantity", "bookBorrowedQuan"};
        Cursor c;
        if(searchTerm != null && searchTerm.length()>0) {
            String sql = "SELECT * FROM "+ TABLE_BOOK +" WHERE "
                    + "bookAuthor "+" LIKE '%"+searchTerm+"%'";
            c = MyDB.rawQuery(sql,null);
            return c;
        }
        c = MyDB.query(TABLE_BOOK, columns,null,null,null,null,null);
        return c;
    }


    public ArrayList<Book> bookSearching(String searchTerm, String search_type){
        bookArrayList.clear();
        openDB();
        Cursor cursor;
        if (search_type.equalsIgnoreCase("book")) {
            cursor = searchByBook(searchTerm);
        }else {
            cursor = searchByAuthor(searchTerm);
        }
        System.out.println("----------bookSearching----------");
        while (cursor.moveToNext()){
            String bookID = cursor.getString(0);
            String bookName = cursor.getString(1);
            String bookAuthor = cursor.getString(2);
            String bookCategory = cursor.getString(3);
            String bookPublishedDate = cursor.getString(4);
            String bookLocation = cursor.getString(5);
            int bookQuantity = cursor.getInt(6);
            int bookBorrowedQuan = cursor.getInt(7);
            Book book = new Book(bookID,bookName,bookAuthor,bookCategory,bookPublishedDate,
                    bookLocation,bookQuantity,bookBorrowedQuan);
            bookArrayList.add(book);
            System.out.println(book);
        }
        closeDB();
        return bookArrayList;
    }

    public void updateBorrowBook(int borrow, int bookID){
        MyDB = this.getWritableDatabase();
        int newBorrow = borrow + 1;
        String query = "UPDATE "+ TABLE_BOOK +"\n"+
                " SET bookBorrowedQuan = " + "'" + newBorrow + "' \n" +
                " Where bookID = " + "'" + bookID + "' ";
        MyDB.execSQL(query);
    }

    //User DB management
    public Boolean insertData(String username, String password){
        MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", username);
        contentValues.put("passWord", password);
        long result = MyDB.insert(TABLE_USERS, null, contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Boolean checkusernamepassword(String userName, String passWord){
        MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from "+ TABLE_USERS +" where userName = ? and passWord = ?",
                new String[] {userName,passWord});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public void updatePass(String pass, String name){
        MyDB = this.getWritableDatabase();
        String query = "UPDATE "+ TABLE_USERS +"\n"+
                        " SET passWord = " + "'" + pass + "' \n" +
                        " Where userName = " + "'" + name + "' ";
        MyDB.execSQL(query);
    }

    public Account getAccount(String name, String pass){
        Account account = null;
        MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from "+ TABLE_USERS +" where userName = ? and passWord = ?",
                new String[] {name,pass});
        System.out.println("----------getAccount----------");
        while (cursor.moveToNext()){
            String userName = cursor.getString(0);
            String userFullName = cursor.getString(1);
            String passWord = cursor.getString(2);
            String userRole = cursor.getString(3);
            String phone = cursor.getString(4);
            String email = cursor.getString(5);
            String gender = cursor.getString(6);
            account = new Account(userName,userFullName, passWord, userRole, phone, email, gender);
        }
        closeDB();
        return account;
    }

    //User SELECT query
    public ArrayList<Account> getAllUser(){
        String role = "user";
        Account account = null;
        MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from "+ TABLE_USERS +" where userRole = ? ",
                new String[] {role});
        System.out.println("----------getAllUser----------");
        while (cursor.moveToNext()){
            String userName = cursor.getString(0);
            String userFullName = cursor.getString(1);
            String passWord = cursor.getString(2);
            String userRole = cursor.getString(3);
            String phone = cursor.getString(4);
            String email = cursor.getString(5);
            String gender = cursor.getString(6);
            account = new Account(userName,userFullName, passWord, userRole, phone, email, gender);
            accountArrayList.add(account);
        }
        closeDB();
        return accountArrayList;
    }



}
