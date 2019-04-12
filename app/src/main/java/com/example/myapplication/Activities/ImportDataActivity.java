package com.example.myapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.Console;
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
        setContentView(R.layout.activity_import_data);


try {

    URL url = new URL("http://compsci02.snc.edu/~nevisw/csci322/ClientServerProj/animals.php");
    URLConnection connection = url.openConnection();
    connection.connect();
    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    String inputLine;

    while((inputLine = in.readLine()) != null)
    {
        System.out.println(inputLine);
    }

    in.close();
    }catch(IOException ex)
        {
        int test = 0;

        }
    }


}
