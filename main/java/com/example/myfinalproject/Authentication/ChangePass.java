package com.example.myfinalproject.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfinalproject.DBManager.DBBook;
import com.example.myfinalproject.Model.Account;
import com.example.myfinalproject.Nav_Drawer_User;
import com.example.myfinalproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChangePass extends AppCompatActivity {
    EditText passO, passN, passC;
    public static final String Email_KEY = "emailKey";
    public static final String Pass_KEY = "passKey";
    public static final String SHARED_PREFS = "shared_prefs_login";
    SharedPreferences sharedPreferences;
    DBBook dbBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        Button hide = findViewById(R.id.showHideButtonn);
        passN = findViewById(R.id.passReset);
        passO = findViewById(R.id.passOld);
        passC = findViewById(R.id.passConfirm);
        Button sub = findViewById(R.id.submitPassBtnn);
        sharedPreferences = this.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        dbBook = new DBBook(this);

        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passN.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD){
                    passN.setInputType(InputType.TYPE_CLASS_TEXT);
                }else {
                    passN.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passold = passO.getText().toString();
                String passnew = passN.getText().toString();
                String passcon = passC.getText().toString();
                if(passold.equals("")) {
                    passO.setError("Old password is required");
                    passO.requestFocus();}
                if(passnew.equals("")){
                    passN.setError("New Password is required");
                    passN.requestFocus();}
                if(passcon.equals("")) {
                    passC.setError("Please enter confirm pass");
                    passC.requestFocus();
                }
                if(passold.equals("")||passnew.equals("")||passcon.equals("")){
                    System.out.println("wrong");
                }
                else{
                    //get user and pass from sharedPreferences
                    String username_email = sharedPreferences.getString(Email_KEY, "");
                    String pass = sharedPreferences.getString(Pass_KEY, "");
                    Account account = dbBook.getAccount(username_email, pass);
                    System.out.println("#name: "+username_email);
                    System.out.println("#pass: "+pass);

                    if(passold.equalsIgnoreCase(account.getPassWord())){
                        if(passcon.equals(passnew)){

                            dbBook.updatePass(passnew, username_email);
                            Toast.makeText(getApplicationContext(), "Change pass successfull", Toast.LENGTH_SHORT).show();

//                            Intent intent  = new Intent(getApplicationContext(), LoginScreenActivity.class);
//                            startActivity(intent);
                        }else{
                            passC.setError("Wrong confirm pass");
                            passC.requestFocus();
                            Toast.makeText(getApplicationContext(), "Please enter confirm pass again", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        passO.setError("Old password is not correct");
                        passO.requestFocus();
                    }
                }
            }
        });
        FloatingActionButton back = findViewById(R.id.floatingBackBtnn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Nav_Drawer_User.class );
                startActivity(intent);
            }
        });
    }

    public void onCLIck(View view){

    }
}