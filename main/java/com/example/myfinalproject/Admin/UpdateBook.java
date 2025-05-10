package com.example.myfinalproject.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myfinalproject.DBManager.DBBook;
import com.example.myfinalproject.Model.Book;
import com.example.myfinalproject.Nav_Drawer_Admin;
import com.example.myfinalproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UpdateBook extends AppCompatActivity {

    private Spinner BookCategory;
    private ArrayAdapter<String> adapter;
    private EditText BookName, BookAuthor, BookPublishedDate, BookQuantity, BookLocation;

    Button EditFin, EditCan;
    private int lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth;
    ImageView imageUpdate;
    ImageButton datePicker2;
    String editID;
    DBBook dbBook;
    SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        dbBook = new DBBook(this);
        findid();
        editData();

        //cancel
        EditCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateBook.this, Nav_Drawer_Admin.class);
                startActivity(intent);
            }
        });

        //Update button
        EditFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(" ========================== Updating ========================== ");
                String bookName = BookName.getText().toString();
                String bookAuthor = BookAuthor.getText().toString();
                String bookCategory = BookCategory.getSelectedItem().toString();
                String bookPublishedDate = BookPublishedDate.getText().toString();
                String bookLocation = BookLocation.getText().toString();
                int bookQuantity = Integer.parseInt(BookQuantity.getText().toString());

//                int quantity = Integer.parseInt(BookQuantity.getText().toString());

                System.out.println("bookName: "+bookName);
                System.out.println("bookAuthor: "+bookAuthor);
                System.out.println("category: "+bookCategory);
                System.out.println("publishdate: "+bookPublishedDate);
                System.out.println("quantity: "+bookQuantity);

                if(bookName.equals("")||bookAuthor.equals("")||bookPublishedDate.equals("")||
                        String.valueOf(bookQuantity).equals("")||bookLocation.equals("")) {
                    Toast.makeText(UpdateBook.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Book book = new Book(editID, bookName,bookAuthor,bookCategory,bookPublishedDate,
                            bookLocation, bookQuantity,0);

                    updateBook(book, editID);


//                    ContentValues cv = new ContentValues();
//                    cv.put("bookName", bookName);
//                    cv.put("bookAuthor", bookAuthor);
//                    cv.put("bookCategory", category);
//                    cv.put("bookPublishedDate", publishdate);
//                    cv.put("bookLocation", bookLocation);
//                    cv.put("bookQuantity", quantity);
//
//                    sqLiteDatabase = dbBook.getReadableDatabase();
//                    long recedit = sqLiteDatabase.update(TABLE_BOOK, cv,"bookID = '"+editID+"'",null);
//                    if (recedit!=-1){
//                        Toast.makeText(UpdateBook.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(UpdateBook.this, "something wrong try again", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });
    }

    //2.load item data from RCV
    private void editData() {
        if (getIntent().getExtras() != null){
            System.out.println(" ========================== Loading transfered data for update ========================== ");
            Bundle data = getIntent().getExtras();
            Book book = data.getParcelable("book_update_data");
            editID = book.getBookID();

            //set text
            BookName.setText(book.getBookName());
            BookAuthor.setText(book.getBookAuthor());
            BookPublishedDate.setText(book.getBookPublishedDate());
            BookQuantity.setText(String.valueOf(book.getBookQuantity()));
            BookLocation.setText(book.getBookLocation());

            //set item selected spinner
            BookCategory.setSelection(adapter.getPosition(book.getBookCategory()));

            System.out.println("=========== INFO UPDATE ============");
            System.out.println("bookID: "+editID);
            System.out.println("bookName: "+book.getBookName());
            System.out.println("bookAuthor: "+book.getBookAuthor());
            System.out.println("bookPublishedDate: "+book.getBookPublishedDate());
            System.out.println("bookQuantity: "+book.getBookQuantity());
            System.out.println("bookLocation: "+book.getBookLocation());
        }
    }

    private boolean updateBook(Book book, String editID) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("book").child(editID);
        //updating artist
        dR.setValue(book);
        Toast.makeText(getApplicationContext(), "Book Updated", Toast.LENGTH_LONG).show();
        return true;
    }
    //1.
    private void findid() {
        BookName = findViewById(R.id.editBookName);
        BookAuthor = findViewById(R.id.editFullName);
        BookPublishedDate = findViewById(R.id.editGender);
        BookQuantity = findViewById(R.id.editgamil);
        BookLocation = findViewById(R.id.editLocation);

        EditFin = findViewById(R.id.buttonFinishUpdate);
        EditCan = findViewById(R.id.buttonCancelUpdate);
        datePicker2 = findViewById(R.id.datePicker2);

        List<String> category = new ArrayList<>();
        category.add("Horror");
        category.add("Adventure");
        category.add("Fantasy");
        category.add("Comedy");
        category.add("Romance");
        category.add("Reference Book");

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, category);
        BookCategory = findViewById(R.id.spinnerCategoryUpdate);
        BookCategory.setAdapter(adapter);

        this.datePicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void buttonSelectDate2() {
        // Date Select Listener.
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                BookPublishedDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                lastSelectedYear = year;
                lastSelectedMonth = monthOfYear;
                lastSelectedDayOfMonth = dayOfMonth;
            }
        };
        DatePickerDialog datePickerDialog = null;
        // Calendar Mode (Default):
        datePickerDialog = new DatePickerDialog(this,
                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
        // Show
        datePickerDialog.show();
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                BookPublishedDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        }, year, month, day);
        dialog.show();
    }
}