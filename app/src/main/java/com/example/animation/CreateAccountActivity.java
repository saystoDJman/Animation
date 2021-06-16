package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {
    EditText cUser,cPass;
    Button createAcc;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        cUser = findViewById(R.id.etvCreateUserName);
        cPass = findViewById(R.id.etvCreatePass);
        createAcc = findViewById(R.id.btnCreateAcc);

        DB = new DBHelper(this);

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = cUser.getText().toString().trim();
                String password = cPass.getText().toString().trim();
                if(uName.trim().isEmpty() && password.trim().isEmpty()){
                    Toast.makeText(getBaseContext(),"Please fill all details",Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkInsertData = DB.insertPlayerData(uName,password,0);
                    if(checkInsertData){
                        Intent intent =new Intent(getBaseContext(),LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(getBaseContext(),"Player Creation Successful",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getBaseContext(),"Player Creation UnSuccessful",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}