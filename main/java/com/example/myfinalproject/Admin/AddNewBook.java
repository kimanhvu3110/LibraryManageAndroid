package com.example.myfinalproject.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myfinalproject.DBManager.Firebase;
import com.example.myfinalproject.Model.Book;
import com.example.myfinalproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddNewBook extends AppCompatActivity {
    private ArrayAdapter adapter;
    Button addBtn;
    private EditText BookName, BookAuthor, publishDate, BookQuantity, BookLocation, BookCate;
    ImageView imageAdd;
    ImageButton datePicker1;
    private int lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth;
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        findid();
        insertData();
    }

    private void insertData() {
        //Submit button
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("=========== INSERT Book ============");
                System.out.println("BookName: " + BookName.getText().toString());
                System.out.println("BookAuthor: " + BookAuthor.getText().toString());
                System.out.println("publishDate: " + publishDate.getText().toString());
                System.out.println("BookCate: " + BookCate.getText().toString());
                System.out.println("BookQuantity: " + BookQuantity.getText().toString());

                String bookName = BookName.getText().toString();
                String bookAuthor = BookAuthor.getText().toString();
                String bookCategory = BookCate.getText().toString();
                String bookPublishedDate = publishDate.getText().toString();
                String bookLocation = BookLocation.getText().toString();
                int bookQuantity = Integer.parseInt(BookQuantity.getText().toString());

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("book");
                //getting a unique id using push().getKey() method
                //it will create a unique id and we will use it as the Primary Key
                String FB_BookID = databaseReference.push().getKey();

                Book book = new Book(FB_BookID, bookName, bookAuthor, bookCategory, bookPublishedDate,
                        bookLocation, bookQuantity, 0);
                databaseReference.child(FB_BookID).setValue(book);
                Toast.makeText(AddNewBook.this, "Book added successfully!", Toast.LENGTH_LONG).show();

                BookName.setText("");
                BookAuthor.setText("");
                BookCate.setText("");
                publishDate.setText("");
                BookLocation.setText("");
                BookQuantity.setText("");
            }
        });
    }

    public void findid(){
        BookName = findViewById(R.id.editBookName);
        BookAuthor = findViewById(R.id.editFullName);
        publishDate = findViewById(R.id.editGender);
        BookQuantity = findViewById(R.id.editgamil);
        BookLocation = findViewById(R.id.editLocation);
        BookCate = findViewById(R.id.editPhone);
        addBtn = findViewById(R.id.buttonAddBook);
        datePicker1 = findViewById(R.id.datePicker1);

        datePicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectDate();
            }
        });
    }

    private void buttonSelectDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                publishDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        }, year, month, day);
        dialog.show();
    }
}