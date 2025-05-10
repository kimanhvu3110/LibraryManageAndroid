package com.example.myfinalproject.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.myfinalproject.Model.Book;
import com.example.myfinalproject.Nav_Drawer_User;
import com.example.myfinalproject.R;

public class BookDetail extends AppCompatActivity {
    private EditText BookName, BookAuthor, publishDate, BookQuantity, BookLocation, BookCate;
    Button cannelBtn;
    ImageView imageAdd;
    ImageButton datePicker;
    private int lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        findid();
        Bundle data = getIntent().getExtras();
        Book book = data.getParcelable("book_detail");

//        int BookQuantity = book.getBookQuantity();

        BookName.setText(book.getBookName());
        BookAuthor.setText(book.getBookAuthor());
        publishDate.setText(book.getBookPublishedDate());
        BookQuantity.setText(Integer.toString(book.getBookQuantity()));
        BookLocation.setText(book.getBookLocation());
        BookCate.setText(book.getBookCategory());

        cannelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetail.this, Nav_Drawer_User.class);
                startActivity(intent);
            }
        });
    }


    //1.
    private void findid() {
        cannelBtn = findViewById(R.id.buttonReturn);

        BookName = findViewById(R.id.editBookName);
        BookAuthor = findViewById(R.id.editFullName);
        publishDate = findViewById(R.id.editDate);
        BookQuantity = findViewById(R.id.editQuantity);
        BookLocation = findViewById(R.id.editLocation);
        BookCate = findViewById(R.id.editPhone);

        BookName.setEnabled(false);
        BookAuthor.setEnabled(false);
        publishDate.setEnabled(false);
        BookQuantity.setEnabled(false);
        BookLocation.setEnabled(false);
        BookCate.setEnabled(false);

    }
}