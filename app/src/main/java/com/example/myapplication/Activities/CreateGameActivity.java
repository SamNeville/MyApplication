package com.example.myapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class CreateGameActivity extends AppCompatActivity {

    Button   mButton;
    EditText mEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        mButton = (Button)findViewById(R.id.createCustomGameButton);
        mEdit   = (EditText)findViewById(R.id.editText1);

        final Intent intent = new Intent(CreateGameActivity.this, QuickGameActivity.class);
        final Bundle b = new Bundle();
        final String[] words = new String[10];

        mButton.setOnClickListener(
            new View.OnClickListener()
            {
                public void onClick(View view)
                {


                    for (int i = 1; i <= 10 ; i++) {
                        switch (i){
                            case 1 :
                                mEdit   = (EditText)findViewById(R.id.editText1);
                                break;
                            case 2 :
                                mEdit   = (EditText)findViewById(R.id.editText2);
                                break;
                            case 3:
                                mEdit   = (EditText)findViewById(R.id.editText3);
                                break;
                            case 4:
                                mEdit   = (EditText)findViewById(R.id.editText4);
                                break;
                            case 5:
                                mEdit   = (EditText)findViewById(R.id.editText5);
                                break;
                            case 6:
                                mEdit   = (EditText)findViewById(R.id.editText6);
                                break;
                            case 7:
                                mEdit   = (EditText)findViewById(R.id.editText7);
                                break;
                            case 8:
                                mEdit   = (EditText)findViewById(R.id.editText8);
                                break;
                            case 9:
                                mEdit   = (EditText)findViewById(R.id.editText9);
                                break;
                            case 10:
                                mEdit   = (EditText)findViewById(R.id.editText10);
                                break;
                        }

                        if (mEdit.getText().toString().matches("")) {
                            Toast.makeText(getApplicationContext(), "Must Fill All 10 Word Fields", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (mEdit.getText().toString().length() <=3) {
                            Toast.makeText(getApplicationContext(), "Words Must Be At Least 3 Characters Long", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (mEdit.getText().toString().length() > 10) {
                            Toast.makeText(getApplicationContext(), "Words Must Be 10 Characters Or Less Long", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        words[i-1] = mEdit.getText().toString();
                    }

                    b.putStringArray("array", words);
                    intent.putExtras(b);
                    startActivity(intent);

                    Log.v("EditText", mEdit.getText().toString());
                }
            });

    }

}