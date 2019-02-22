package com.example.myapplication.Activities;
import java.lang.*;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.R;
import com.example.myapplication.Services.gameService;

import org.w3c.dom.Text;

import java.util.*;

public class QuickGameActivity extends AppCompatActivity {

    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_game);

        int columns = 10;
        int rows = 10;
        String[] words = new String[]{"Sam", "Nate", "Matt", "William", "Gary", "Fred", "Sofia", "penny", "owen", "cashdadd", "selena", "gus"};

        String[][] gameArray = new String[rows][columns];
        gameArray = gameService.createGame(words, rows, columns); // calling method in the game service that creates a new game
        String[] total = new String [rows*columns];

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
                Toast.makeText(getApplicationContext(), ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
