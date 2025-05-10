package com.example.myfinalproject.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myfinalproject.Model.BookingItem;
import com.example.myfinalproject.Admin.UpdateBook;
import com.example.myfinalproject.Model.Book;
import com.example.myfinalproject.R;

import java.util.ArrayList;

public class RecycleViewAdapterAdmin_Book extends RecyclerView.Adapter
        <RecycleViewAdapterAdmin_Book.AdminRecycleViewHolder>{
    Context context;
    ArrayList<Book> bookArrayList;

    public RecycleViewAdapterAdmin_Book(Context context, ArrayList<Book> bookArrayList) {
        this.context = context;
        this.bookArrayList = bookArrayList;
    }

    @NonNull
    @Override
    public AdminRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_admin_layout, parent, false);
        AdminRecycleViewHolder userView = new AdminRecycleViewHolder(view);
        return userView;
    }

    @Override
    public void onBindViewHolder(AdminRecycleViewHolder holder, int position) {
        final Book book = bookArrayList.get(position);

        holder.picture.setImageResource(R.drawable.demobook);
        holder.textViewName.setText(book.getBookName());

        //click on button go to main activity
        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("===========================Send data to update class=============================");
                System.out.println(book);

                Bundle bundle = new Bundle();
                bundle.putParcelable("book_update_data", book);
                Intent intent = new Intent(context, UpdateBook.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    public static class AdminRecycleViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        TextView textViewName;
        Button detail, collect;
        Button updateButton, DeleteButton;

        public AdminRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.pictureBook);
            textViewName = itemView.findViewById(R.id.textViewID);

//            detail = itemView.findViewById(R.id.detailBookBtn);
//            collect = itemView.findViewById(R.id.collectbtn);

            //Admin
            updateButton = itemView.findViewById(R.id.update_book_btn);
//            DeleteButton = itemView.findViewById(R.id.buttonRVDelete);
        }
    }
}