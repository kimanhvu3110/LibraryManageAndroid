package com.example.myfinalproject.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinalproject.Model.Book;
import com.example.myfinalproject.Nav_Drawer_User;
import com.example.myfinalproject.R;

public class UserDetail extends AppCompatActivity {
    private EditText AccountName, FullName, phone, gender, BookLocation, BookCate;
    Button cannelBtn;
    ImageView imageAdd;
    ImageButton datePicker;
    private int lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        findid();
        Bundle data = getIntent().getExtras();

        Book book = data.getParcelable("user_detail");

        AccountName.setText(book.getBookName());
        FullName.setText(book.getBookAuthor());
        gender.setText(book.getBookPublishedDate());
        phone.setText(Integer.toString(book.getBookQuantity()));
        cannelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetail.this, Nav_Drawer_User.class);
                startActivity(intent);
            }
        });
    }

    //1.
    private void findid() {
        cannelBtn = findViewById(R.id.buttonReturn);
        AccountName = findViewById(R.id.editBookName);
        FullName = findViewById(R.id.editFullName);
        gender = findViewById(R.id.editGender);
        phone = findViewById(R.id.editPhone);

        AccountName.setEnabled(false);
        FullName.setEnabled(false);
        gender.setEnabled(false);
        phone.setEnabled(false);
    }
}