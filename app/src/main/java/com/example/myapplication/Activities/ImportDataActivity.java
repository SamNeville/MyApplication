package com.example.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.logging.ConsoleHandler;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;
import java.net.MalformedURLException;
import java.io.BufferedInputStream;

import com.example.myapplication.R;

public class ImportDataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Intent intent = new Intent(ImportDataActivity.this, QuickGameActivity.class);
        final Bundle b = new Bundle();
        final String[] words = new String[10];

        setContentView(R.layout.activity_import_data);
        Button scienceButton = findViewById(R.id.button5);
        Button carsButton = findViewById(R.id.button6);
        Button animalsButton = findViewById(R.id.button7);

            scienceButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                try
                {
                    URL url = new URL("http://compsci02.snc.edu/~nevisw/csci322/ClientServerProj/science.txt");

                    String line;
                    String text = new String() ;
                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                    while ((line = in.readLine()) != null) {
                        if(line != null)
                        text = text + line;
                    }
                    in.close();
                    String[] gameWords = new String[10];
                    String delims = "[,]+";
                    gameWords = text.split(delims);
                    b.putStringArray("science", gameWords);
                    intent.putExtras(b);
                    startActivity(intent);
                } catch (MalformedURLException e) {
                } catch (IOException e) {
                }
            }
        });
        carsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try
                {
                    URL url = new URL("http://compsci02.snc.edu/~nevisw/csci322/ClientServerProj/cars.txt");
                    String line;
                    String text = new String() ;
                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                    while ((line = in.readLine()) != null) {
                        if(line != null)
                            text = text + line;
                    }
                    in.close();

                    String[] gameWords = new String[10];
                    String delims = "[,]+";
                    gameWords = text.split(delims);
                    b.putStringArray("cars", gameWords);
                    intent.putExtras(b);
                    startActivity(intent);
                } catch (MalformedURLException e) {
                } catch (IOException e) {
                }
            }
        });
        animalsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            try
            {
                URL url = new URL("http://compsci02.snc.edu/~nevisw/csci322/ClientServerProj/animals.txt");
                String line;
                String text = new String() ;
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                while ((line = in.readLine()) != null) {
                    if(line != null)
                        text = text + line;
                }
                in.close();
                String[] gameWords = new String[10];
                String delims = "[,]+";
                gameWords = text.split(delims);
                b.putStringArray("animals", gameWords);
                intent.putExtras(b);
                startActivity(intent);
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }
        }
        });
    }
}
