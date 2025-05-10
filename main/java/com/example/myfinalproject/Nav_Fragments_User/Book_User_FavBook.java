package com.example.myfinalproject.Nav_Fragments_User;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinalproject.DBManager.DBBook;
import com.example.myfinalproject.Model.Account;
import com.example.myfinalproject.Model.Book;
import com.example.myfinalproject.R;
import com.example.myfinalproject.RecyclerView.RecycleViewAdapterAdmin_Book;
import com.example.myfinalproject.RecyclerView.RecycleViewAdapterUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Book_User_FavBook extends Fragment {
    private RecycleViewAdapterUser recycleViewAdapterUser;
    Context context;
    DBBook dbBook;
    RecyclerView recyclerView;
    ArrayList<Book> bookArrayList;
    String search_type = "book";
    View view;
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "shared_prefs_login";
    public static final String Email_KEY = "emailKey";
    public static final String Pass_KEY = "passKey";
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        dbBook = new DBBook(context);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_book_user, container, false);

        recycleViewAdapterUser = new RecycleViewAdapterUser(context, bookArrayList);
        recyclerView = view.findViewById(R.id.recycleViewBookUser);

        bookArrayList = new ArrayList<>();
        firebaseGetBook();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("book");
//        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
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
                recyclerView.setAdapter(recycleViewAdapterUser);
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
            searchView.setQueryHint("Search by book...");
        }else {
            searchView.setQueryHint("Search by author...");
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
                searchBook(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchBook(String keyword) {
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
                    switch (search_type) {
                        case "book":
                            if (book.getBookName().toLowerCase().contains(keyword.toLowerCase())) {
                                bookArrayList.add(book);
                            }
                            break;
                        case "author":
                            if (book.getBookAuthor().toLowerCase().contains(keyword.toLowerCase())) {
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
                recyclerView.setAdapter(recycleViewAdapterUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }



    //
    private void BookRCVdisplay() {
        System.out.println("=====================================BookRCVdisplay=============================================");
        sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String username_email = sharedPreferences.getString(Email_KEY, "");
        String pass = sharedPreferences.getString(Pass_KEY, "");
        Account account = dbBook.getAccount(username_email, pass);
        System.out.println("#name: "+username_email);
        System.out.println("#pass: "+pass);

        bookArrayList = dbBook.selectFavBook(account.getUserName());
        recycleViewAdapterUser = new RecycleViewAdapterUser(context, bookArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(recycleViewAdapterUser);
    }
}
