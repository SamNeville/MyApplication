package com.example.myapplication.Activities;
import java.lang.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.Services.gameService;

import java.util.*;

public class QuickGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_game);

        int columns = 10;
        int rows = 10;
        String[] words = new String[]{"Sam", "Nate", "Matt", "William", "Gary", "Fred", "Sofia", "penny", "owen", "cashdadd", "selena", "gus"};

        String[][] gameArray = new String[rows][columns];
        gameArray = gameService.createGame(words, rows, columns); // calling method in the game service that creates a new game

        //for filling in the empty spaces of the grid
        for(int i=0; i<columns; i++){
            for(int j=0; j<rows; j++){
                Random r = new Random();
                char c = (char)(r.nextInt(26) + 'a');
                String s = String.valueOf(c).toUpperCase(); // we will only play the game with uppercase letters
                if(gameArray[i][j] == null) gameArray[i][j] = s; // only place it, if its not occupied already
            }
        }
        String newArr [][] = gameArray;
    }



}
