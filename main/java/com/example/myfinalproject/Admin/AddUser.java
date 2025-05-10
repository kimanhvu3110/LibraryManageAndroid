package com.example.myfinalproject.Admin;

import static com.example.myfinalproject.DBManager.DBBook.TABLE_USERS;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.myfinalproject.DBManager.DBBook;
import com.example.myfinalproject.Model.Account;
import com.example.myfinalproject.Model.Book;
import com.example.myfinalproject.Nav_Drawer_Admin;
import com.example.myfinalproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddUser extends AppCompatActivity {
    private ArrayAdapter adapterSpinnerRole;
    private Spinner spinnerAccRole;
    private Button addBtn;
    private FloatingActionButton cannelBtn;
    private EditText AccName, StdName, StdDOB;
    ImageView imageAdd;
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String FRAGMENT_TAG = "fragment_tag";

    ImageButton datePicker;
    private int lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth;
    DBBook dbBook;
    SQLiteDatabase sqLiteDatabase;


    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        dbBook = new DBBook(this);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        findid();
        insertData();
    }

    private void insertData() {
        //Submit button
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("=========== INSERT User ============");
                System.out.println("userName: "+AccName.getText().toString());
                String email = AccName.getText().toString();
                String role = spinnerAccRole.getSelectedItem().toString();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("account");
                String FB_AccID = databaseReference.push().getKey();
                Account account = new Account(email, role);
                databaseReference.child(FB_AccID).setValue(account);
                Toast.makeText(AddUser.this, "Account added successfully!", Toast.LENGTH_LONG).show();
                AccName.setText("");
            }
        });

        cannelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(FRAGMENT_TAG, "user_manager");
                editor.apply();

                Intent intent = new Intent(AddUser.this, Nav_Drawer_Admin.class);
                startActivity(intent);
            }
        });
    }

    private void findid() {
        AccName = findViewById(R.id.addAccName);
        addBtn = findViewById(R.id.buttonAddNew);
        cannelBtn = findViewById(R.id.cancelBackBtnn);

        //spinner
        List<String> roles = new ArrayList<>();
        roles.add("user");
        roles.add("account");
        adapterSpinnerRole = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, roles);
        spinnerAccRole = findViewById(R.id.spinnerRole);
        spinnerAccRole.setAdapter(adapterSpinnerRole);
    }




}
