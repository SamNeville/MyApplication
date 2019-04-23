package com.example.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DTOs.SaveData;
import com.example.myapplication.DTOs.WordTracker;
import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

      //String[] foundWords = new String[10];
      // int[]positions = new int[10];
      // int index = 0;
      // for (int i = 0; i <saveData.allWords.length-1 ; i++) {
      // if(index > foundWords.length) break;
      // foundWords[index] = saveData.allWords[i].word;
      // index++;
      // for (int j = 0; j <saveData.wordsLeft.length ; j++) {
      // if(saveData.allWords[i].word.equals(saveData.wordsLeft[j])){
      // foundWords[index] = null;
      // index--;
      // }
      // }
      // }
      // for (int i = 0; i <= foundWords.length-1 ; i++) {
      // if(foundWords[i] != null) {
      // positions = gridFiller(foundWords[i], saveData.allWords, 10, 10);
      // for (int j = 0; j <= positions.length - 1; j++) {
      // GridViewItems = (TextView) grid.getChildAt(positions[j]);
      // GridViewItems.setBackgroundColor(Color.parseColor("#93dada"));
      // GridViewItems.setTextColor(Color.parseColor("#fdfcfa"));
      // }
      // }
      // }


public class ResumeGameActivity extends AppCompatActivity {
    int wordRemoveCounter = 2;

    int firstPosX = 0;
    int firstPosY = 0;
    int deleteCount = 0;

    int secondPosX = 0;
    int secondPosY = 0;

    int posCountTracker = 0;
    int highlightTracker = 0;
    int backPosition = -1;
    int hintCount = 3;


    GridView grid;
    ListView list;
    Button saveGameButton;
    Button giveHintButton;
    String selectedItem = null;
    TextView GridViewItems, BackSelectedItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_game);
        saveGameButton = findViewById(R.id.button3);
        giveHintButton = findViewById(R.id.button);

        final int columns = 10;
        final int rows = 10;
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

                final SaveData saveData = new SaveData();
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

                saveGameButton.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {
                        gameSaver(saveData.game, saveData.wordsLeft, saveData.allWords, getApplicationContext());
                    }
                });

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



                for (int i = 0; i <=saveData.wordsLeft.length-1 ; i++) {
                    if( saveData.wordsLeft[i] == null){
                        saveData.wordsLeft[i] = "";
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
                String[] tempWords = saveData.wordsLeft;
                int endCount = saveData.wordsLeft.length-1;
                int smallIndex=0;
                for (int i = 0; i <= tempWords.length-1 ; i++) {
                    if(!tempWords[i].equals("") && !tempWords[i].equals(" ") && !tempWords[i].equals(".")){
                        saveData.wordsLeft[smallIndex] = tempWords[i];
                        //saveData.wordsLeft[endCount] = "";
                        endCount--;
                        smallIndex++;
                        wordRemoveCounter = smallIndex;
                    }
                }
                wordListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, saveData.wordsLeft); // should display these words under the grid now
                list.setAdapter(wordListAdapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView parent, View v, int position, long id) {
                        Toast.makeText(getApplicationContext(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                    }
                });

                grid = (GridView) findViewById(R.id.gameGrid);
                final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, gameBoardArray);
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
                            if(saveData.allWords[i] != null)
                                if (saveData.allWords[i] != null && ((saveData.allWords[i].beginX == firstPosX && saveData.allWords[i].beginY == firstPosY && saveData.allWords[i].endX == secondPosX && saveData.allWords[i].endY == secondPosY) || (saveData.allWords[i].beginX == firstPosY && saveData.allWords[i].beginY == firstPosX && saveData.allWords[i].endX == secondPosY && saveData.allWords[i].endY == secondPosX))){
                                    String selectedWord = saveData.allWords[i].word;

                                    int counter = 0;

                                    for (int j = 0; j <=saveData.wordsLeft.length ; j++) {
                                        if(saveData.wordsLeft[i].equals(selectedWord)){

                                        }
                                    }


                                    //more problems here
                                    String[] tempWords = saveData.wordsLeft;
                                    int endCount = saveData.wordsLeft.length-1;
                                    int smallIndex=0;
                                    for (int j = 0; j <= tempWords.length-1 ; j++) {
                                        if(!tempWords[j].equals("") && !tempWords[j].equals(" ") && !tempWords[j].equals(".") && !tempWords[j].equals(selectedWord)){
                                            saveData.wordsLeft[smallIndex] = tempWords[j];
                                            smallIndex++;
                                            wordRemoveCounter = smallIndex;
                                        }
                                    }

                                    for (int j = 0; j < saveData.wordsLeft.length ; j++) {
                                        if(saveData.wordsLeft[j] != selectedWord) {
                                            saveData.wordsLeft[counter] = saveData.wordsLeft[j];
                                        } else counter--;
                                        counter++;
                                    }

                                    int temp = (saveData.wordsLeft.length - wordRemoveCounter-2);
                                    for (int j = 0; j < saveData.wordsLeft.length ; j++) {
                                        if(j > temp){
                                            saveData.wordsLeft[j] = "";
                                        }
                                    }

                                    int[] fillerLocations = new int[selectedWord.length()];
                                    fillerLocations = gridFiller(selectedWord, saveData.allWords, rows, columns);

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

                giveHintButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        hintCount = giveHint(saveData.wordsLeft,saveData.allWords,adapter, grid, hintCount);

                        //calls hint method to highlight the grid for a hint. you get 3 hints. updates on screen.
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






    public int[] gridFiller(String word, WordTracker[] data, int rows, int columns){
        int [] response = new int[word.length()];
        WordTracker selectedWord = new WordTracker("",0,0,0,0);
        int length = -1;

        for (int i = 0; i < data.length; i++) {
            if(data[i] != null && !data[i].word.equals("")){
                length++;
            }
        }

        for (int i = 0; i <= length ; i++) {
            String s = data[i].word;
            if(!data[i].word.equals("") && word.equals(data[i].word)){
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

            for (int i = 0; i <= words.length-1 ; i++) {
                if(!words[i].equals("") && words[i] != "" && words[i] != null && !words[i].equals(null)){
                    wordsLeft = wordsLeft + words[i]+ ",";
                }
            }
            wordsLeft = wordsLeft +"\n";

            for (int i = 0; i <= 9 ; i++) {

                if(wordList[i] != null && wordList[i].word != null){
                    allWords = allWords + wordList[i].word + ",{" + wordList[i].beginX + "},{" + wordList[i].beginY + "},{" + wordList[i].endX + "},{" + wordList[i].endY + "},";
                }
            }
            allWords = allWords + "\n";

            if(currentGame != null){
                for (int i = 0; i <=game.length-1 ; i++) {
                    for (int j = 0; j <game.length ; j++) {
                        currentGame = currentGame + game[i][j] + ",";
                    }
                }
            }
            currentGame = currentGame + "\n";


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

    public int giveHint(String[] wordsLeft,WordTracker[] allWords, ArrayAdapter arrayAdapter, GridView grid, int hintCount) {
        Object parent = grid.getParent();

        if (hintCount != 0) {
            hintCount--;
            boolean success = false;
            String selectedWord = "";
            int selectedWordY = -1;
            int selectedWordX = -1;


            while (success == false) {
                Random r = new Random();
                int rand = r.nextInt((wordsLeft.length - 0) + 0) + 0;

                for (int i = 0; i <= wordsLeft.length - 1; i++) {
                    if (i == rand && !wordsLeft[i].equals("") && wordsLeft[i] != "") {
                        selectedWord = wordsLeft[i];
                        success = true;
                        break;
                    }
                }
            }

            for (int i = 0; i <= allWords.length - 1; i++) {
                if (i <= allWords.length - 1 && allWords[i] != null) {
                    if (allWords[i].word.equals(selectedWord)) {
                        selectedWordX = allWords[i].beginX;
                        selectedWordY = allWords[i].beginY;
                    }
                }
            }

            View v = grid.getSelectedView();

            if (selectedWordX != -1 && selectedWordY != -1) {
                int pos = selectedWordX * 10 + selectedWordY;

                GridViewItems = (TextView) grid.getChildAt(pos);
                GridViewItems.setBackgroundColor(Color.parseColor("#93dada"));
                GridViewItems.setTextColor(Color.parseColor("#fdfcfa"));
                hintCount--;
                EditText hints = (EditText) findViewById(R.id.editText2);
            } else {
                hintCount++;
                Toast.makeText(getApplicationContext(), "Couldn't give hint... hint returned", Toast.LENGTH_SHORT).show();
                EditText hints = (EditText) findViewById(R.id.editText2);
            }

        }else Toast.makeText(getApplicationContext(), "Sorry! No more hints", Toast.LENGTH_SHORT).show();

        return hintCount;
    }

    public void openVictory(){
        Intent intent = new Intent(this, victoryActivity.class);
        startActivity(intent);
    }


}
