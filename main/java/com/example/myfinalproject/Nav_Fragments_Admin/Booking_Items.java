package com.example.myfinalproject.Nav_Fragments_Admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinalproject.Admin.AddBookBorrowing;
import com.example.myfinalproject.DBManager.Firebase;
import com.example.myfinalproject.Model.BookingItem;
import com.example.myfinalproject.R;


import com.example.myfinalproject.RecyclerView.RecycleViewAdapterAdmin_Booking_Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Booking_Items extends Fragment {
    private Button addBtn, cannelBtn;
    private EditText addBookID, addEmail, addBorrowDate;

    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "shared_prefs";

    ImageButton datePicker;
    private int lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth;
    Firebase firebase;
    ArrayList<com.example.myfinalproject.Model.BookingItem> bookingItems;
    private RecycleViewAdapterAdmin_Booking_Item recycleViewAdapterAdmin_booking_item;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    Context context;
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_book_admin, container, false);
        sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);


        bookingItems = new ArrayList<>();
        recycleViewAdapterAdmin_booking_item = new RecycleViewAdapterAdmin_Booking_Item(context, bookingItems);
        recyclerView = view.findViewById(R.id.recycleViewBookAdmin);

        FloatingActionButton fab = view.findViewById(R.id.floatBtnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddBookBorrowing.class);
                startActivity(intent);
            }
        });

        firebaseGetBookingItem();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("booking_item");
    }

    public void firebaseGetBookingItem(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                bookingItems.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    BookingItem bookingItem = postSnapshot.getValue(BookingItem.class);
                    //adding artist to the list
                    bookingItems.add(bookingItem);
                }
                System.out.println("recycleViewAdapterAdmin_booking_item");
                System.out.println("bookingItems"+bookingItems);
                //creating adapter
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
                recyclerView.setAdapter(recycleViewAdapterAdmin_booking_item);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
