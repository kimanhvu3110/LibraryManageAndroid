package com.example.myfinalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myfinalproject.Nav_Fragments_Admin.Booking_Items;
import com.example.myfinalproject.Nav_Fragments_Admin.Book_Manager;
import com.example.myfinalproject.Nav_Fragments_Admin.User_Manager;
import com.example.myfinalproject.Nav_Fragments_User.Settings;
import com.google.android.material.navigation.NavigationView;

public class Nav_Drawer_Admin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String Email_KEY = "emailKey";

    private static final int FRAG_User_Manager = 0;
    private static final int FRAG_Book_Manager = 1;
    private static final int FRAG_Setting = 2;
    private static final int FRAG_Book_Add = 3;

    public static final String FRAGMENT_TAG = "fragment_tag";
    public static Context contextOfApplication;
    private int mCurFrag;

    TextView name;
    SharedPreferences sharedPreferences;
    String email;
    String frag_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_admin);//1.Main Nav Layout

        contextOfApplication = getApplicationContext();

        //2. NavigationView layout
        NavigationView navigationView = findViewById(R.id.navigation_view_admin);
        View header = navigationView.getHeaderView(0);
        //3. Nav Header layout
        name = header.findViewById(R.id.Nav_header);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        email = sharedPreferences.getString(Email_KEY, null);
        name.setText(email);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //4. Nav Whole Drawer Layout
        drawerLayout = findViewById(R.id.nav_drawer_layout_admin);//drawer_layout - main activity
        //ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //NavigationView
        navigationView.setNavigationItemSelectedListener(this);

        frag_tag = sharedPreferences.getString(FRAGMENT_TAG, null);
        System.out.println("1.mCurFrag: "+mCurFrag);
        System.out.println("frag_tag: "+frag_tag);
        if(frag_tag != null && frag_tag.equalsIgnoreCase("user_manager")){
            replaceFrag(new User_Manager());
            navigationView.getMenu().findItem(R.id.nav_user_manager).setChecked(true);
            editor.remove(FRAGMENT_TAG).apply();
        }else {
            mCurFrag = FRAG_Book_Manager;
            replaceFrag(new Book_Manager());
            navigationView.getMenu().findItem(R.id.nav_book_manager).setChecked(true);
        }
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        System.out.println("mCurFrag: "+mCurFrag);
        int id = item.getItemId();
        if (id == R.id.nav_user_manager){
            if (mCurFrag != FRAG_User_Manager) {
                replaceFrag(new User_Manager());
                mCurFrag = FRAG_User_Manager;
            }
        }
        else if (id == R.id.nav_book_manager){
            if (mCurFrag != FRAG_Book_Manager) {
                replaceFrag(new Book_Manager());
                mCurFrag = FRAG_Book_Manager;
            }
        }else if (id == R.id.nav_setting){
            if (mCurFrag != FRAG_Setting) {
                replaceFrag(new Settings());
                mCurFrag = FRAG_Setting;
            }
        }else if (id == R.id.nav_book_add){
            if (mCurFrag != FRAG_Book_Add) {
                replaceFrag(new Booking_Items());
                mCurFrag = FRAG_Book_Add;
            }
        }
        else if (id == R.id.nav_logout){
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
