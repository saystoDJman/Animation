package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {
    EditText cUser,cPass;
    Button createAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        cUser = findViewById(R.id.etvCreateUserName);
        cPass = findViewById(R.id.etvCreatePass);
        createAcc = findViewById(R.id.btnCreateAcc);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = cUser.getText().toString().trim();
                String password = cPass.getText().toString().trim();
                if(uName.isEmpty() && password.isEmpty()){
                    Toast.makeText(getBaseContext(),"Please fill all details",Toast.LENGTH_SHORT).show();
                }
                else{

                }
            }
        });


    }
}