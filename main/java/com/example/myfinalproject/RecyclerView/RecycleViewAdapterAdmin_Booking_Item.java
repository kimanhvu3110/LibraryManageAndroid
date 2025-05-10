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
import com.example.myfinalproject.R;

import java.util.ArrayList;


public class RecycleViewAdapterAdmin_Booking_Item extends RecyclerView.Adapter <RecycleViewAdapterAdmin_Booking_Item.AdminRecycleViewHolder>{
    Context context;
    ArrayList<BookingItem> bookingItems;

    public RecycleViewAdapterAdmin_Booking_Item(Context context, ArrayList<BookingItem> bookingItems) {
        this.context = context;
        this.bookingItems = bookingItems;
    }

    @NonNull
    @Override
    public AdminRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item_layout, parent, false);
        AdminRecycleViewHolder userView = new AdminRecycleViewHolder(view);
        return userView;
    }

    @Override
    public void onBindViewHolder(AdminRecycleViewHolder holder, int position) {
        final BookingItem bookingItem = bookingItems.get(position);
        holder.textBookNameShow.setText(bookingItem.getBookName());
        holder.textAccountShow.setText(bookingItem.getUserName());
        holder.textBorrowDateShow.setText(bookingItem.getBorrowDate());
        holder.textRDateShow.setText(bookingItem.getReturnDate());
    }

    @Override
    public int getItemCount() {
        return bookingItems.size();
    }

    public static class AdminRecycleViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        TextView textBookNameShow, textAccountShow, textBorrowDateShow, textRDateShow;
        Button updateButton;

        public AdminRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            textBookNameShow = itemView.findViewById(R.id.textBookNameShow);
            textAccountShow = itemView.findViewById(R.id.textAccountShow);
            textBorrowDateShow = itemView.findViewById(R.id.textBorrowDateShow);
            textRDateShow = itemView.findViewById(R.id.textRDateShow);


            //Admin
            updateButton = itemView.findViewById(R.id.update_book_btn);
//            DeleteButton = itemView.findViewById(R.id.buttonRVDelete);
        }
    }
}