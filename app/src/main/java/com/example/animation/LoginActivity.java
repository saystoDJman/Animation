package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText username,password;
    TextView signup;
    DBHelper DB;
    public static String loginPlayer = "";
    public static String loginPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.etvUserName);
        password = findViewById(R.id.etvPassword);
        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.tvSignUp);

        DB = new DBHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!username.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("")){
                    String givenName = username.getText().toString().trim();
                    String givenPassword = password.getText().toString().trim();
                    Cursor res = DB.getPlayer(givenName);

                    String name = "", pass = "";

                    if(res.getCount() != 0){
                        while (res.moveToNext()) {
                            name = res.getString(0);
                            pass = res.getString(1);
                        }
                        if(givenName.equals(name) && givenPassword.equals(pass)){
                            loginPlayer = name;
                            loginPassword = pass;
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(getBaseContext(),StartGameActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(getApplicationContext(),"No Player Exists with given User Name",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Enter all Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getBaseContext(),CreateAccountActivity.class);
                startActivity(intent);
            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


}