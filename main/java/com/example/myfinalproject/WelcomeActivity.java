package com.example.myfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myfinalproject.Authentication.LoginScreenActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setupSlideAnimation();

        Button btn = findViewById(R.id.loginBtn);
    }

    private void setupSlideAnimation() {
        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.slide);
        slide.setSlideEdge(Gravity.LEFT);
        View decor = getWindow().getDecorView();
        getWindow().setEnterTransition(slide);
        getWindow().setExitTransition(slide);
    }

    public void onCLick(View view){
        Toast.makeText(WelcomeActivity.this, "Welcome to login", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoginScreenActivity.class);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent, bundle);
    }
}