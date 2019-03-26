package com.example.myapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.Toast;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;

public class CreateAccountActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText e1, e2, e3;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        db = new DatabaseHelper(this);

    e1 = (EditText) findViewById(R.id.username);
    e2 = (EditText) findViewById(R.id.password);
    e3 = (EditText) findViewById(R.id.password2);
    b1 = (Button) findViewById(R.id.button2);

    b1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v){
            String s1 = e1.getText().toString();
            String s2 = e2.getText().toString();
            String s3 = e3.getText().toString();

            if(s1.equals("") || s2.equals("") || s3.equals("")) {
                Toast.makeText(getApplicationContext(), "Fields Are Empty", Toast.LENGTH_SHORT).show();

            }
            else {
                if(s2.equals(s3)){
                    Boolean chkUser = db.chkUsername(s1);
                    if(chkUser == true){
                        Boolean insert = db.insert(s1, s2);
                        if(insert == true){
                            Toast.makeText(getApplicationContext(), "Registered User Successfully", Toast.LENGTH_LONG).show();
                        }
                    } else{
                        Toast.makeText(getApplicationContext(), "Username Already Taken", Toast.LENGTH_SHORT).show();
                    }
                }else Toast.makeText(getApplicationContext(),"Passwords Do Not Match", Toast.LENGTH_SHORT).show();
            }

        }
    });

    }
}
