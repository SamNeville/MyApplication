package com.example.myapplication.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.DTOs.SaveData;
import com.example.myapplication.DTOs.WordTracker;
import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResumeGameActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_game);


        String resumeGameData = "";

        try {
            InputStream inputStream = getApplicationContext().openFileInput("SavedGameData.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                resumeGameData = stringBuilder.toString();

                SaveData saveData = new SaveData();
                saveData.wordsLeft = new String[10];
                saveData.allWords = new WordTracker[10];
                saveData.game = new String[10][10];
                String delims = "[:{},]+";
                String[] savedDataString = resumeGameData.split(delims);
                Boolean wordsLeft = false;
                Boolean allWords = false;
                Boolean game = false;
                int counter = 0;
                int internalCounter = 0;

                for (int i = 0; i < savedDataString.length ; i++) {
                    if(savedDataString[i].equals("WordsLeft"))
                    {
                        wordsLeft = true;
                        allWords  = false;
                        game = false;
                        counter=0;
                        i++;
                    }
                    if(savedDataString[i].equals("AllWords"))
                    {
                        wordsLeft = false;
                        allWords  = true;
                        game = false;
                        counter=0;
                        i++;
                    }
                    if(savedDataString[i].equals("Game"))
                    {
                        wordsLeft = false;
                        allWords  = false;
                        game = true;
                        counter=0;
                        internalCounter=0;
                        i++;
                    }
                    if(wordsLeft) {saveData.wordsLeft[counter] = savedDataString[i]; counter++;}
                    if(allWords){
                        if (internalCounter == 0){
                            WordTracker tracker = new WordTracker(" ", 0,0,0,0);
                            saveData.allWords[counter] = tracker;
                            saveData.allWords[counter].word = savedDataString[i];
                        }
                        if(internalCounter == 1){
                            saveData.allWords[counter].beginX = Integer.parseInt(savedDataString[i]);
                        }
                        if(internalCounter == 2){
                            saveData.allWords[counter].beginY = Integer.parseInt(savedDataString[i]);
                        }
                        if(internalCounter == 3){
                            saveData.allWords[counter].endX = Integer.parseInt(savedDataString[i]);
                        }
                        if(internalCounter == 4){
                            saveData.allWords[counter].endY = Integer.parseInt(savedDataString[i]);
                            internalCounter =-1;
                            counter++;
                        }
                        internalCounter++;
                    }
                    if(game) {
                        if(counter == 10){
                            counter = 0;
                            internalCounter++;
                        }
                        saveData.game[counter][internalCounter] = savedDataString[i];
                        counter++;
                    }
                }
                int test = 0;
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

    }



}
