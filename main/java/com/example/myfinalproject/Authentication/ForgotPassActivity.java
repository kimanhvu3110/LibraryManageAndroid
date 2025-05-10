package com.example.myfinalproject.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfinalproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class ForgotPassActivity extends AppCompatActivity {
    TabLayout tabLayout;
    EditText editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Forgot"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this, tabLayout.getTabCount());
        tabLayout.setAlpha(0);
        tabLayout.setTranslationY(300);
        editEmail = findViewById(R.id.emailReset);
        FloatingActionButton back = findViewById(R.id.floatingBackBtnn);

        Button reset = findViewById(R.id.submitPassBtnn);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(ForgotPassActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editEmail.setError("Email is required");
                    editEmail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(ForgotPassActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    editEmail.setError("Valid email is required");
                    editEmail.requestFocus();
                }else{
                    String mess = getRandomString();
                    resetPassword(mess);
                }
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginScreenActivity.class );
                startActivity(intent);
            }
        });

    }

    public static String getRandomString(){
        return UUID.randomUUID().toString().substring(0,7);
    }

    public void resetPassword(String mess) {
        final String username = "librarymanagement3genius@gmail.com";
        final String password = "pjyssuufzgjnkygz";
        String messageSend = "Your new password is " + mess;
        Properties properties = new Properties();
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.user", username);
        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        session.setDebug(true);
        System.out.println("okkkkkkkkk");
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(editEmail.getText().toString()));
            message.setSubject("New password for Library Management App");
            message.setText(messageSend);
            Transport.send(message);
            Toast.makeText(getApplicationContext(),"Email send successfully", Toast.LENGTH_LONG).show();
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}