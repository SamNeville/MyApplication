package com.example.myapplication.Activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DTOs.SaveData;
import com.example.myapplication.DTOs.WordTracker;
import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResumeGameActivity extends AppCompatActivity {

    GridView grid;
    ListView list;
    Button saveGameButton;
    String selectedItem = null;
    TextView GridViewItems, BackSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_game);

        saveGameButton = findViewById(R.id.button3);

        String resumeGameData = "";
        final ArrayAdapter wordListAdapter;

        try {
            InputStream inputStream = getApplicationContext().openFileInput("SavedGameData.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);//receives saved game data
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
                saveData.game = new String[10][10];// creates game data object for easier manipulation
                String[] gameBoardArray = new String[100];
                String delims = "[:{},]+";
                String[] savedDataString = resumeGameData.split(delims);
                Boolean wordsLeft = false;
                Boolean allWords = false;
                Boolean game = false;
                int counter = 0;
                int internalCounter = 0;

                for (int i = 0; i < savedDataString.length ; i++) { //parses txt data into object
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
                    if(wordsLeft) {
                        for (int j = 0; j <saveData.wordsLeft.length ; j++) {

                        }
                        saveData.wordsLeft[counter] = savedDataString[i]; counter++;
                    }
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
                int gameBoardXCounter = 0;
                int gameBoardYCounter = 0;

                for (int i = 0; i <=99 ; i++) {
                        gameBoardArray[i] = saveData.game[gameBoardXCounter][gameBoardYCounter];
                        gameBoardXCounter++;
                         if (gameBoardXCounter == 10) {
                            gameBoardXCounter = 0;
                            gameBoardYCounter++;
                        }
                }

                //create resume game grid and others
                list = (ListView)findViewById(R.id.wordsView); // find the list view from xml
                wordListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, saveData.wordsLeft); // should display these words under the grid now
                list.setAdapter(wordListAdapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView parent, View v, int position, long id) {
                        Toast.makeText(getApplicationContext(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                    }
                });

                grid = (GridView) findViewById(R.id.gameGrid);
                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, gameBoardArray);
                grid.setAdapter(adapter);
                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView parent, View v, int position, long id) {

                    }
                });


            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

    }



}
