package com.example.myfinalproject.RecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfinalproject.Admin.AddUser;
import com.example.myfinalproject.DBManager.DBBook;
import com.example.myfinalproject.Model.Account;
import com.example.myfinalproject.Model.FavBook;
import com.example.myfinalproject.User.BookDetail;
import com.example.myfinalproject.Model.Book;
import com.example.myfinalproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecycleViewAdapterUser extends RecyclerView.Adapter<RecycleViewAdapterUser.UserRecycleViewHolder>{
    private Context context;
    ArrayList<Book> bookArrayList = new ArrayList<>();
    public static final String SHARED_PREFS = "shared_prefs_login";
    public static final String Email_KEY = "emailKey";
    public static final String Pass_KEY = "passKey";
    DBBook dbBook;


    public RecycleViewAdapterUser(Context context, ArrayList<Book> bookArrayList) {
        this.context = context;
        this.bookArrayList = bookArrayList;
        dbBook = new DBBook(context);
    }

    @NonNull
    @Override
    public UserRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_user_layout, parent, false);
        UserRecycleViewHolder userView = new UserRecycleViewHolder(view);
        return userView;
    }

    @Override
    public void onBindViewHolder( UserRecycleViewHolder holder, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        final Book book = bookArrayList.get(position);
        String mDrawableName = "myimg";
        int resID = Context.getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
        holder.picture.setImageResource(R.drawable.demobook);

        holder.textViewName.setText(book.getBookName());
        int bookAvaiQuan = book.getBookQuantity() - book.getBookBorrowedQuan();
        holder.bookQuanAvi.setText(String.valueOf(bookAvaiQuan));

        //click on button go to main activity
        holder.detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("===========================Send data to update class=============================");
                System.out.println("getBookID: "+book.getBookID());
                Bundle bundle = new Bundle();
                bundle.putParcelable("book_detail", book);
                Intent intent = new Intent(context, BookDetail.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    public static class UserRecycleViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView textViewName, bookQuanAvi;
        Button detailBtn;

        public UserRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.pictureBook);
            textViewName = itemView.findViewById(R.id.textViewID);
            bookQuanAvi = itemView.findViewById(R.id.textBookAvaiQuan);

            detailBtn = itemView.findViewById(R.id.detailBookBtn);


        }
    }


}