package com.example.myapplication.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;

public class SignInActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText e1, e2;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        db = new DatabaseHelper(this);

        e1 = (EditText) findViewById(R.id.username);
        e2 = (EditText) findViewById(R.id.password);
        b1 = (Button) findViewById(R.id.signInButton);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();

                if (s1.equals("") || s2.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields Are Empty", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean insert = db.chkSignIn(s1, s2);
                    if (insert) {
                        Toast.makeText(getApplicationContext(), "User successfully signed in", Toast.LENGTH_LONG).show();
                    } else Toast.makeText(getApplicationContext(), "Username and/or password is incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}