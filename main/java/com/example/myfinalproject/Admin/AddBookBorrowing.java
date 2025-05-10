package com.example.myfinalproject.Admin;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinalproject.DBManager.DBBook;
import com.example.myfinalproject.Model.Book;
import com.example.myfinalproject.Model.BookingItem;
import com.example.myfinalproject.Nav_Drawer_Admin;
import com.example.myfinalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class AddBookBorrowing extends com.example.myfinalproject.Admin.AppCompatActivity {
    private Button addBtn, cannelBtn;
    private Spinner BookNameList;
    private EditText addBookName, addEmail, addBorrowDate, addReturnedDate;
    private ArrayAdapter<String> adapter;

    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "shared_prefs";
    DatabaseReference databaseReference;
    ImageButton datePicker, datePickerReturn;
    DBBook dbBook;
    Book book;
    ArrayList<Book> bookArrayList;
    ArrayList<String> bookName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_borrow);
        dbBook = new DBBook(this);
        book = new Book();
        bookArrayList = new ArrayList<>();
        bookName = new ArrayList<>();
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        databaseReference = FirebaseDatabase.getInstance().getReference("book");
        firebaseGetBookName();//get book name list
        findid();
        insertData();
    }

    private void insertData() {
        //Submit button
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("=========== INSERT Booking Item ============");
                //insert
//                String bookCategory = BookCategory.getSelectedItem().toString();
                String bookName = BookNameList.getSelectedItem().toString();
                String email = addEmail.getText().toString();
                String borrowDate = addBorrowDate.getText().toString();
                String returnedDate = addReturnedDate.getText().toString();

                Book newbook = getBook(bookName);
                if (newbook.getBookBorrowedQuan() < newbook.getBookQuantity()){
                    int newBookBorrowQuan = newbook.getBookBorrowedQuan()+1;
                    newbook.setBookBorrowedQuan(newBookBorrowQuan);
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("book").child(newbook.getBookID());
                    dR.setValue(newbook);

                    //insert booking
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("booking_item");
                    //getting a unique id using push().getKey() method
                    //it will create a unique id and we will use it as the Primary Key
                    String FB_BookingItemID = databaseReference.push().getKey();

                    BookingItem bookingItem = new BookingItem(FB_BookingItemID, bookName, email, borrowDate, returnedDate, "0");
                    databaseReference.child(FB_BookingItemID).setValue(bookingItem);
                    Toast.makeText(AddBookBorrowing.this, "Booking Item added successfully!", Toast.LENGTH_LONG).show();

                    addBookName.setText("");
                    addEmail.setText("");
                    addBorrowDate.setText("");
                    addReturnedDate.setText("");
                }else{
                    Toast.makeText(getApplicationContext(), "No Book Available!", Toast.LENGTH_LONG).show();
                }

            }
        });
        
        cannelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBookBorrowing.this, Nav_Drawer_Admin.class);
                startActivity(intent);
            }
        });
    }
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                addBorrowDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        }, year, month, day);
        dialog.show();
    }
    private void showReturnDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                addReturnedDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        }, year, month, day);
        dialog.show();
    }
    private void findid() {
//        addBookName = findViewById(R.id.addBookID);
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, bookName);
        BookNameList = findViewById(R.id.spinnerBookName);
        BookNameList.setAdapter(adapter);

        addEmail = findViewById(R.id.addEmail);
        addBorrowDate = findViewById(R.id.addBorrowDate);
        addReturnedDate = findViewById(R.id.addReturnedDate);

        addBtn = findViewById(R.id.buttonAddBorrow);
        cannelBtn = findViewById(R.id.buttonCancel);
        datePicker = findViewById(R.id.datePickerBorrow);
        datePickerReturn = findViewById(R.id.datePickerReturn);

        this.datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        this.datePickerReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReturnDatePickerDialog();
            }
        });
    }

    public void firebaseGetBookName(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                bookName.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Book book = postSnapshot.getValue(Book.class);
                    //adding artist to the list
                    bookName.add(book.getBookName());
                }
                //creating adapter
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public Book getBook(String keyword){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                bookArrayList.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    book = postSnapshot.getValue(Book.class);
                    //adding artist to the list
                    if (book.getBookName().equals(keyword)){
                        bookArrayList.add(book);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return book;
    }

}
