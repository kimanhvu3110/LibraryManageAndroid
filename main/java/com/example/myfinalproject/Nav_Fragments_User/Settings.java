package com.example.myfinalproject.Nav_Fragments_User;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfinalproject.Authentication.ChangePass;
import com.example.myfinalproject.ContactActivity;
import com.example.myfinalproject.R;

public class Settings extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        Button change = root.findViewById(R.id.changepass);
        Button contact = root.findViewById(R.id.contactBtn);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity().getApplicationContext(), ChangePass.class);
                startActivity(intent);
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity().getApplicationContext(), ContactActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}
