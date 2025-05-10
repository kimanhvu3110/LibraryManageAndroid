package com.example.myfinalproject.Nav_Fragments_Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinalproject.Admin.AddBookBorrowing;
import com.example.myfinalproject.Admin.AddNewBook;
import com.example.myfinalproject.DBManager.DBBook;
import com.example.myfinalproject.Model.Book;
import com.example.myfinalproject.R;
import com.example.myfinalproject.RecyclerView.RecycleViewAdapterAdmin_Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Book_Manager extends Fragment {
    private RecycleViewAdapterAdmin_Book recycleViewAdapterAdmin_book;
    RecyclerView recyclerView;

    Context context;
    DBBook dbBook;
    ArrayList<Book> bookArrayList;
    String search_type = "book";
    View view;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        dbBook = new DBBook(context);
        bookArrayList = new ArrayList<>();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_book_admin, container, false);

        recycleViewAdapterAdmin_book = new RecycleViewAdapterAdmin_Book(context, bookArrayList);
        recyclerView = view.findViewById(R.id.recycleViewBookAdmin);

        FloatingActionButton fab = view.findViewById(R.id.floatBtnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNewBook.class);
                startActivity(intent);
            }
        });
        firebaseGetBook();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("book");
    }

    public void firebaseGetBook(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                bookArrayList.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Book book = postSnapshot.getValue(Book.class);
                    //adding artist to the list
                    bookArrayList.add(book);
                }
                //creating adapter
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                recyclerView.setAdapter(recycleViewAdapterAdmin_book);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_book:
                search_type = "book";
                break;
            case R.id.search_author:
                search_type = "author";
                break;
        }
        System.out.println("*onOptionsItemSelected*");
        System.out.println("search_type: "+search_type);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        if (search_type.equalsIgnoreCase("book")) {
            searchView.setQueryHint("Search by book");
        }else{
            searchView.setQueryHint("Search by author");
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("----------onQueryTextChange----------");
                System.out.println("###newText: "+newText);
//                bookArrayList = dbBook.bookSearching(newText, search_type);
//                recyclerView.setAdapter(recycleViewAdapterAdmin_book);
                searchBook(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchBook(String keyword){
//        Query query = databaseReference.limitToFirst(2);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                bookArrayList.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Book book = postSnapshot.getValue(Book.class);
                    //adding artist to the list
                    switch(search_type) {
                        case "book":
                            if(book.getBookName().toLowerCase().contains(keyword.toLowerCase())){
                                bookArrayList.add(book);
                            }
                            break;
                        case "author":
                            if(book.getBookAuthor().toLowerCase().contains(keyword.toLowerCase())){
                                bookArrayList.add(book);
                            }
                            break;
                        default:
                            break;
                    }
                }
                //creating adapter
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                recyclerView.setAdapter(recycleViewAdapterAdmin_book);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Book book = snapshot.getValue(Book.class);
//                bookArrayList.clear();
//                if (book != null){
//                    if(book.getBookName().contains(keyword)){
//                        bookArrayList.add(book);
//                    }
//                    //creating adapter
//                    recyclerView.setHasFixedSize(true);
//                    recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
//                    recyclerView.setAdapter(recycleViewAdapterAdmin_book);
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Book book = snapshot.getValue(Book.class);
//                if (book == null || bookArrayList == null || bookArrayList.isEmpty()){
//                    return;
//                }
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }




    private void BookRCVdisplay() {
        System.out.println("=====================================BookRCVdisplay=============================================");
        bookArrayList = dbBook.selectBook();
        recycleViewAdapterAdmin_book = new RecycleViewAdapterAdmin_Book(context, bookArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(recycleViewAdapterAdmin_book);
    }
}
