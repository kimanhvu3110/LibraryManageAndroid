package com.example.myfinalproject.DBManager;

import android.text.TextUtils;
import android.widget.Toast;

import com.example.myfinalproject.Model.Book;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase {

    private void addBook(Book book) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("artists");
        //checking if the value is provided
//        if (!TextUtils.isEmpty(name)) {
            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String FB_BookID = databaseReference.push().getKey();
            //creating an Artist Object
//            Book book = new Book(id, name, track);
            //Saving the Artist
            databaseReference.child(FB_BookID).setValue(book);
            //displaying a success toast
//            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();
//        } else {
//            //if the value is not given displaying a toast
//            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
//        }
    }
}
