package com.example.myapplication.Activities;
import java.lang.*;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.DTOs.GameData;
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

    GridView grid;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_game);

        final int columns = 10;
        final int rows = 10;
        String[] words = new String[]{"Sam", "Nate", "Matt", "William", "Gary", "Fred", "Sofia", "penny", "owen", "cashdadd", "selena", "gus"};

        ListView listView; // for word list under word search
        ArrayAdapter wordListAdapter;  // create out adapter to front end


        GameData gameData = new GameData();        //for capturing the game
        String[][] gameArray = new String[rows][columns];   //for displaying the game

        gameData = gameService.createGame(words, rows, columns); // call to service to get a game

        String[] wordlList = new String[gameData.Words.length]; // for displaying the words
        gameArray = gameData.Game; // grabs the game
        wordlList = gameData.Words; // grabs the words

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


        grid = (GridView) findViewById(R.id.gameGrid);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, total);

        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                int x = position%rows;
                int y = position/columns;

                if(posCountTracker == 0){
                    posCountTracker++;
                    firstPosX = x;
                    firstPosY = y;
                }

                if(posCountTracker ==1){
                    posCountTracker--;
                    secondPosX = x;
                    secondPosY = y;
                }

                Toast.makeText(getApplicationContext(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "[ " + String.valueOf(firstPosX) + " ]" + " " +  "[ " + String.valueOf(firstPosY) + " ]"+ "[ "+String.valueOf(secondPosX)+" ]" + " " +  "[ " +String.valueOf(secondPosY)+" ]", Toast.LENGTH_SHORT).show();


            }
        });


        list = (ListView)findViewById(R.id.wordsView); // find the list view from xml
        wordListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, wordlList); // should display these words under the grid now

        list.setAdapter(wordListAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
