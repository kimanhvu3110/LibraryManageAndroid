package com.example.myfinalproject.Authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.myfinalproject.DBManager.DBBook;
import com.example.myfinalproject.Model.Account;
import com.example.myfinalproject.Nav_Drawer_Admin;
import com.example.myfinalproject.Nav_Drawer_User;
import com.example.myfinalproject.R;
import com.example.myfinalproject.RecyclerView.RecycleViewAdapterAdmin_User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class LoginTabFragment extends Fragment {
    View forgetpass, logBtn, hideBtn;
    Button hide;
    EditText email, pass;
    DBBook dbUserRole;
    Context context;
    DatabaseReference databaseReference;

    ArrayList<Account> accountArrayList;
    String checkLogin = "false";
    Account account;
    boolean check = false;

    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "shared_prefs_login";
    public static final String Email_KEY = "emailKey";
    public static final String Pass_KEY = "passKey";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);
        context = container.getContext();

        accountArrayList = new ArrayList<>();

        //User stuff
        forgetpass = root.findViewById(R.id.forgetpass);
        email = (EditText)root.findViewById(R.id.email);
        pass = (EditText)root.findViewById(R.id.pass);

        sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        logBtn = root.findViewById(R.id.loginButton);
        hideBtn = root.findViewById(R.id.showHideButtonn);
        hide = (Button)root.findViewById(R.id.showHideButtonn);
        forgetpass.setTranslationX(800);
        email.setTranslationX(800);
        pass.setTranslationX(800);
        logBtn.setTranslationX(800);
        hideBtn.setTranslationX(800);
        forgetpass.setAlpha(0);
        email.setAlpha(0);
        pass.setAlpha(0);
        hideBtn.setAlpha(0);
        logBtn.setAlpha(0);
        forgetpass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        logBtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        hideBtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        dbUserRole = new DBBook(getActivity());

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = email.getText().toString();
                String passw = pass.getText().toString();

                //Check input
                //username = email
                if(user.equals("")||passw.equals("")) {
                    Toast.makeText(getActivity(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    email.setError("Email is required");
                    email.requestFocus();
                    pass.setError("Password is required");
                    pass.requestFocus();
                }
                else{

//                    Boolean checkuserpass = dbUserRole.checkusernamepassword(user, passw);
//                    String email2 = sharedPreferences.getString(Email_KEY, null);



                    System.out.println("mail: "+user);
                    System.out.println("passw: "+passw);

                    for(int i=0; i<accountArrayList.size(); i++) {
                        account = accountArrayList.get(i);
                        System.out.println("account: "+account);
                        if(account.getEmail().equals(user)&&account.getPassWord().equals(passw)){
                            check = true;
                            System.out.println("1.check: "+check);
                            System.out.println("TRUE");
                            break;
                        }
                    }

                    System.out.println("2.check: "+check);
                    //if user and password correct
                    if(check){
                        Toast.makeText(getActivity(), "Sign in successfull", Toast.LENGTH_SHORT).show();
//                        Intent intent  = new Intent(getActivity().getApplicationContext(), MainUserScreenActivity.class);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Email_KEY, email.getText().toString());
                        editor.putString(Pass_KEY, pass.getText().toString());
                        editor.apply();
                        check = false;

                        Intent intent;
                        if (account.getUserRole().equalsIgnoreCase("admin")){
                            intent = new Intent(getActivity().getApplicationContext(), Nav_Drawer_Admin.class);
                        }else {
                            intent = new Intent(getActivity().getApplicationContext(), Nav_Drawer_User.class);
                        }
                        startActivity(intent);
                    }else{
                        Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD){
                    pass.setInputType(InputType.TYPE_CLASS_TEXT);
                }else {
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("account");
        firebaseCheckLogin();
    }

    public void firebaseCheckLogin(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                accountArrayList.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Account account = postSnapshot.getValue(Account.class);
                    //adding artist to the list
                    accountArrayList.add(account);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
