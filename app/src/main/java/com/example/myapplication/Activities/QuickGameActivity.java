package com.example.myapplication.Activities;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.*;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


import com.example.myapplication.DTOs.Coordinates;
import com.example.myapplication.DTOs.GameData;
import com.example.myapplication.DTOs.SaveData;
import com.example.myapplication.DTOs.WordTracker;
import com.example.myapplication.R;
import com.example.myapplication.Services.gameService;

import org.w3c.dom.Text;

import java.util.*;

public class QuickGameActivity extends AppCompatActivity {

    int firstPosX = 0;
    int firstPosY = 0;

    int secondPosX = 0;
    int secondPosY = 0;

    int posCountTracker = 0;
    int highlightTracker = 0;
    int backPosition = -1;

    GridView grid;
    ListView list;
    Button saveGameButton;


    String selectedItem = null;
    TextView GridViewItems, BackSelectedItem;

    int wordRemoveCounter = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_game);

        saveGameButton = findViewById(R.id.button3);


        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        Bundle a = intent.getExtras();
        String[] words = new String[]{"wolves", "cougars", "snakeys", "lioness", "tigers", "bears", "turtley", "giraffer", "hippopot", "bingod"};

        if(b != null){
            words = b.getStringArray("array");
        }
        if(a!=null){
            words = a.getStringArray("saved");
        }

        final int columns = 10;
        final int rows = 10;

        ListView listView; // for word list under word search
        final ArrayAdapter wordListAdapter;  // create out adapter to front end

        //saveGameButton.setOnClickListener();

        GameData gameData = new GameData();        //for capturing the game
        String[][] gameArray = new String[rows][columns];   //for displaying the game
        final WordTracker[] WordData;

        gameData = gameService.createGame(words, rows, columns); // call to service to get a game
        gameArray = gameData.Game; // grabs the game
        final String[][] saveGameArray = gameArray;
        final String[] wordList = gameData.Words; // grabs the words
        WordData = gameData.WordDetails; // grabs Words and their start and end coordinates

        saveGameButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                gameSaver(saveGameArray, wordList, WordData, getApplicationContext());
            }
        });

        String[] total = new String [rows*columns]; // used for gridview since it can only take single dimensional arrays

        int k = 0;
        //for filling in the empty spaces of the grid
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                Random r = new Random();
                char c = (char) (r.nextInt(26) + 'a');
                String s = String.valueOf(c).toUpperCase(); // we will only play the game with uppercase letters
                if (gameArray[i][j] == null) {
                    gameArray[i][j] = s; // only place it, if its not occupied already
                    total[k] = gameArray[i][j];
                    k++;
                } else {total[k] = gameArray[i][j]; k++;}
            }
        }

        list = (ListView)findViewById(R.id.wordsView); // find the list view from xml
        wordListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, wordList); // should display these words under the grid now
        list.setAdapter(wordListAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        grid = (GridView) findViewById(R.id.gameGrid);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, total);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                if(posCountTracker == 0){
                    int x = position%rows;
                    int y = position/columns;
                    posCountTracker++;
                    highlightTracker = 1;
                    firstPosX = x;
                    firstPosY = y;
                    secondPosY = -1;
                    secondPosX = -1;
                    int positionA = position;
                } else {
                    int x = position%rows;
                    int y = position/columns;
                    posCountTracker--;
                    highlightTracker = 2;
                    secondPosX = x;
                    secondPosY = y;
                    int positionB = position;
                }


                for (int i = 0; i < 10; i++) {
                    if(WordData[i] != null)
                    if (WordData[i] != null && ((WordData[i].beginX == firstPosX && WordData[i].beginY == firstPosY && WordData[i].endX == secondPosX && WordData[i].endY == secondPosY) || (WordData[i].beginX == firstPosY && WordData[i].beginY == firstPosX && WordData[i].endX == secondPosY && WordData[i].endY == secondPosX))){
                        String selectedWord = WordData[i].word;

                        int[] fillerLocations = new int[selectedWord.length()];
                        fillerLocations = gridFiller(selectedWord, WordData, rows, columns);

                        int counter = 0;
                        for (int j = 0; j < wordList.length ; j++) {
                            if(wordList[j] != selectedWord) {
                                wordList[counter] = wordList[j];
                            } else counter--;
                            counter++;
                        }

                        int temp = (wordList.length - wordRemoveCounter);
                        for (int j = 0; j < wordList.length ; j++) {
                            if(j > temp){
                                wordList[j] = "";
                            }
                        }
                        wordRemoveCounter++;

                        wordListAdapter.notifyDataSetChanged();

                        for (int j = 0; j < fillerLocations.length; j++) {
                            if(highlightTracker ==1) {
                                selectedItem = parent.getItemAtPosition(fillerLocations[j]).toString();
                                GridViewItems = (TextView) v;
                                GridViewItems.setBackgroundColor(Color.parseColor("#93dada"));
                                GridViewItems.setTextColor(Color.parseColor("#fdfcfa"));
                                BackSelectedItem = (TextView) grid.getChildAt(backPosition);
                                if (backPosition != -1) {
                                    BackSelectedItem.setSelected(false);
                                    BackSelectedItem.setBackgroundColor(Color.parseColor("#93dada"));
                                    BackSelectedItem.setTextColor(Color.parseColor("#fdfcfa"));
                                }
                                backPosition = fillerLocations[j];
                            }

                            if(highlightTracker ==2) {
                                selectedItem = parent.getItemAtPosition(fillerLocations[j]).toString();
                                GridViewItems = (TextView) v;
                                GridViewItems.setBackgroundColor(Color.parseColor("#93dada"));
                                GridViewItems.setTextColor(Color.parseColor("#fdfcfa"));
                                BackSelectedItem = (TextView) grid.getChildAt(backPosition);
                                if (backPosition != -1) {
                                    BackSelectedItem.setSelected(false);
                                    BackSelectedItem.setBackgroundColor(Color.parseColor("#93dada"));
                                    BackSelectedItem.setTextColor(Color.parseColor("#fdfcfa"));
                                }
                                backPosition = fillerLocations[j];
                            }
                        }
                    }
                }
                Toast.makeText(getApplicationContext(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "[ " + String.valueOf(firstPosX) + " ]" + " " +  "[ " + String.valueOf(firstPosY) + " ]"+ "[ "+String.valueOf(secondPosX)+" ]" + " " +  "[ " +String.valueOf(secondPosY)+" ]", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public int[] gridFiller(String word, WordTracker[] data, int rows, int columns){
        int [] response = new int[word.length()];
        WordTracker selectedWord = new WordTracker("",0,0,0,0);
        int length = -1;

        for (int i = 0; i < data.length; i++) {
            if(data[i] != null){
                length++;
            }
        }

        for (int i = 0; i < length ; i++) {
            if(data[i].word != "" && word == data[i].word){
                selectedWord = data[i];
            }
        }

        int startY = selectedWord.beginX;
        int startX = selectedWord.beginY;
        int endY = selectedWord.endX;
        int endX = selectedWord.endY;
        int currentX = startX;
        int currentY = startY;

        response[0] = currentX + rows*currentY;

        for (int i = 1; i < word.length() ; i++) {
            if(startX > endX){
                startX--;
                currentX = startX;
            }
            if(startY > endY){
                startY--;
                currentY = startY;
            }
            if(startX < endX){
                startX++;
                currentX = startX;
            }
            if(startY < endY){
                startY++;
                currentY = startY;
            }

            response[i] = currentX + rows*currentY;
        }
            response[word.length()-1] = endX + rows*endY;
        return response;
    }

    public void gameSaver(String game[][], String words[], WordTracker[] wordList, Context context) {
        try{
            SaveData saveGameData = new SaveData();
            saveGameData.game = game;
            saveGameData.allWords = wordList;
            saveGameData.wordsLeft = words;
            String wordsLeft = "WordsLeft:";
            String allWords = "AllWords:";
            String currentGame = "Game:";
            File file = new File(getFilesDir(), "SavedGameData.txt");

            for (int i = 0; i <= words[i].length() ; i++) {
                if(words[i]!=""){
                    wordsLeft = wordsLeft + words[i]+ ",";
                }

            }

            wordsLeft = wordList +"\n";
            for (int i = 0; i <= 9 ; i++) {

                if(wordList[i] != null && wordList[i].word != null){
                    allWords = allWords + wordList[i].word + ",";
                }

            }
            allWords = allWords + "\n";

            if(currentGame != null){
                for (int i = 0; i <=game.length-1 ; i++) {
                    for (int j = 0; j <game.length-1 ; j++) {
                        currentGame = currentGame + game[i][j] + ",";
                    }

                }
            }


            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(wordsLeft);
            bufferedWriter.write(allWords);
            bufferedWriter.write(currentGame);
            bufferedWriter.close();
        }catch (IOException ex){

        }

    };

}
