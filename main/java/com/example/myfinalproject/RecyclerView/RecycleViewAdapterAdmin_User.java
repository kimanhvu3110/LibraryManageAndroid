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

import com.example.myfinalproject.Admin.UserDetail;
import com.example.myfinalproject.Model.Account;
import com.example.myfinalproject.R;
import com.example.myfinalproject.User.BookDetail;

import java.util.ArrayList;

public class RecycleViewAdapterAdmin_User extends RecyclerView.Adapter<RecycleViewAdapterAdmin_User.AdminRecycleViewHolder>{
    Context context;
    ArrayList<Account> accountArrayList;


    public RecycleViewAdapterAdmin_User(Context context, ArrayList<Account> accountArrayList) {
        this.context = context;
        this.accountArrayList = accountArrayList;
    }

    @NonNull
    @Override
    public AdminRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout, parent, false);
        AdminRecycleViewHolder userView = new AdminRecycleViewHolder(view);
        return userView;
    }

    @Override
    public void onBindViewHolder(AdminRecycleViewHolder holder, int position) {
        final Account account = accountArrayList.get(position);

        System.out.println("User name: " + account.getUserName());
        holder.accName.setText(account.getUserName());

        //click on button go to main activity
        holder.detailUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("user_detail", account);
                Intent intent = new Intent(context, UserDetail.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }

    public static class AdminRecycleViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        TextView accName;

        Button detailUser;

        public AdminRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
//            picture = itemView.findViewById(R.id.accountImage);
            accName = itemView.findViewById(R.id.textAccName);
            detailUser = itemView.findViewById(R.id.buttonDetailUser);

        }
    }
}