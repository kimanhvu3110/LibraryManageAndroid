package com.example.myfinalproject.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfinalproject.DBManager.DBBook;
import com.example.myfinalproject.R;
import com.google.android.material.tabs.TabLayout;

public class LoginScreenActivity extends AppCompatActivity  {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setAlpha(0);
        tabLayout.setTranslationY(300);
        Button forget = findViewById(R.id.forgetpass);
        EditText username =  findViewById(R.id.email);
        EditText password =  findViewById(R.id.pass);
    }

    public void onCLicK(View view){
        Toast.makeText(LoginScreenActivity.this, "You can reset password now!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ForgotPassActivity.class );
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent, bundle);
    }


}