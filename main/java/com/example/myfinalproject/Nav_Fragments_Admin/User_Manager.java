package com.example.myfinalproject.Nav_Fragments_Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinalproject.Admin.AddUser;
import com.example.myfinalproject.DBManager.DBBook;
import com.example.myfinalproject.Model.Account;
import com.example.myfinalproject.Model.Book;
import com.example.myfinalproject.R;
import com.example.myfinalproject.RecyclerView.RecycleViewAdapterAdmin_User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User_Manager extends Fragment {
    private RecycleViewAdapterAdmin_User recycleViewAdapterAdmin_user;
    Context context;
    DBBook dbBook;
    RecyclerView recyclerView;
    ArrayList<Account> accountArrayList;

    DatabaseReference databaseReference;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        dbBook = new DBBook(context);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_manager, container, false);

        recyclerView = view.findViewById(R.id.recycleViewUserList);
        FloatingActionButton fab = view.findViewById(R.id.floatBtnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddUser.class);
                startActivity(intent);
            }
        });
        accountArrayList = new ArrayList<>();
        firebaseGetBook();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("account");
    }

    public void firebaseGetBook(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                accountArrayList.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Account account = postSnapshot.getValue(Account.class);
                    //adding artist to the list
                    accountArrayList.add(account);
                }
                //creating adapter
                recycleViewAdapterAdmin_user = new RecycleViewAdapterAdmin_User(context, accountArrayList);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                recyclerView.setAdapter(recycleViewAdapterAdmin_user);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.search_book:
//                search_type = "book";
//                break;
//            case R.id.search_author:
//                search_type = "author";
//                break;
//        }
//        System.out.println("*onOptionsItemSelected*");
//        System.out.println("search_type: "+search_type);
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.toolbar_search, menu);
//        MenuItem menuItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//
//        if (search_type.equalsIgnoreCase("book")) {
//            searchView.setQueryHint("Search by book");
//        }else {
//            searchView.setQueryHint("Search by author");
//        }
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                System.out.println("----------onQueryTextChange----------");
//                System.out.println("###newText: "+newText);
//                bookSearching(newText);
//                return true;
//            }
//        });
//        super.onCreateOptionsMenu(menu, inflater);
//    }

//    private void bookSearching(String searchTerm) {
//        bookArrayList.clear();
//        dbBook.openDB();
//        Cursor cursor;
//        if (search_type.equalsIgnoreCase("book")) {
//            cursor = dbBook.searchByBook(searchTerm);
//        }else {
//            cursor = dbBook.searchByAuthor(searchTerm);
//        }
//        System.out.println("----------bookSearching----------");
//        while (cursor.moveToNext()){
//            int bookID = cursor.getInt(0);
//            String bookName = cursor.getString(1);
//            String bookAuthor = cursor.getString(2);
//            String bookCategory = cursor.getString(3);
//            String bookPublishedDate = cursor.getString(4);
//            String bookLocation = cursor.getString(5);
//            String bookQuantity = cursor.getString(6);
//
//            bookArrayList.add(new Book(bookID,bookName,bookAuthor,bookCategory,bookPublishedDate,bookLocation,bookQuantity));
//        }
//        dbBook.closeDB();
//        recyclerView.setAdapter(recycleViewAdapterAdmin_book);
//    }



}
