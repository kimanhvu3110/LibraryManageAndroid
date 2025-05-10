package com.example.myfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfinalproject.Nav_Fragments_User.Home;
import com.example.myfinalproject.Nav_Fragments_User.Book_User_FavBook;
import com.example.myfinalproject.Nav_Fragments_User.Settings;
import com.google.android.material.navigation.NavigationView;

public class Nav_Drawer_User extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String Email_KEY = "emailKey";

    private static final int FRAG_Home = 0;
    private static final int FRAG_Book = 1;
    private static final int FRAG_Setting = 2;
    public static Context contextOfApplication;
    private int mCurFrag = FRAG_Home;

    TextView name;
    SharedPreferences sharedPreferences;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_user);//1.Main Nav Layout

        contextOfApplication = getApplicationContext();

        //2. NavigationView layout
        NavigationView navigationView = findViewById(R.id.navigation_view_user);
        View header = navigationView.getHeaderView(0);
        //3. Nav Header layout
        name = header.findViewById(R.id.Nav_header);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(Email_KEY, null);
        name.setText(email);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //4. Nav Whole Drawer Layout
        drawerLayout = findViewById(R.id.nav_drawer_layout_user);//drawer_layout - main activity
        //ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //NavigationView
        navigationView.setNavigationItemSelectedListener(this);

        replaceFrag(new Home());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.nav_profile){
//            startActivity(new Intent(Nav_Drawer_Manager.this, MainUserScreenActivity.class));
//        }else if (id == R.id.nav_setting){
//            startActivity(new Intent(Nav_Drawer_Manager.this, MainUserScreenActivity.class));
//        }else if (id == R.id.nav_book){
//            startActivity(new Intent(Nav_Drawer_Manager.this, MainUserScreenActivity.class));
//        }else if (id == R.id.nav_logout){
//            startActivity(new Intent(Nav_Drawer_Manager.this, MainUserScreenActivity.class));
//        }
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            if (mCurFrag != FRAG_Home) {
                replaceFrag(new Home());
                mCurFrag = FRAG_Home;
            }
        }
        else if (id == R.id.nav_book){
            if (mCurFrag != FRAG_Book) {
                replaceFrag(new Book_User_FavBook());
                mCurFrag = FRAG_Book;
            }
        }else if (id == R.id.nav_setting){
            if (mCurFrag != FRAG_Setting) {
                replaceFrag(new Settings());
                mCurFrag = FRAG_Setting;
            }
        }else if (id == R.id.nav_logout){
            Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
            Toast.makeText(getContextOfApplication(), "Goodbye !", Toast.LENGTH_SHORT).show();
            startActivity(i);
            SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void replaceFrag(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}