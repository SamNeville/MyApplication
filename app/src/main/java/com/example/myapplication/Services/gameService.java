package com.example.myapplication.Services;
import android.provider.UserDictionary;

import com.example.myapplication.DTOs.GameData;
import com.example.myapplication.DTOs.Response;
import com.example.myapplication.DTOs.Coordinates;
import com.example.myapplication.DTOs.WordTracker;

import org.jetbrains.annotations.NotNull;

import java.lang.*;
import java.util.Random;

@SuppressWarnings("MagicConstant")
public class gameService {
    public static GameData createGame(String[] words, int columns, int rows){  // create game method takes in a word list and an array size - creates a word search with the words passed in and returns it in the response.value
        GameData response = new GameData();
        response.Words = new String[words.length];
        String [] gameWords = new String[words.length];

        try {
            if(columns != rows) throw new IllegalArgumentException("rows and columns must be the same for grid");
            if(words.length == 0) throw new IllegalArgumentException("Cannot Pass an Empty List of Words");
            if(words == null) throw new IllegalArgumentException("Cannot Pass a null list");

            String[][] game = new String[rows][columns];
            for(int i = 0; i<game.length; i++){
                for(int j = 0; j<game.length; j++){
                    game[i][j] = ".";
                }
            }
            int [][] directions = {{0,1},{0,-1},{-1,0},{1,0},{1,1},{1,-1},{-1,-1},{-1,1}};
            Response val = new Response();
            Random rand = new Random();

            int direction = rand.nextInt(8); //picks a random direction
            int min = 0;
            int max = rows;
            int limiter = 0;
            int finalCoordinateCounter = -1;

            int coordinateX = rand.nextInt((max - min) + 1) + min; // picks a random x
            int coordinateY = rand.nextInt((max - min) + 1) + min; // picks a random y
            WordTracker[] finalCoordinates = new WordTracker[100];

            for (int i = 0; i < words.length; i++)
            {
                if(limiter > 1000){
                    limiter = 0;
                    i++;
                }

                if(i == 9){
                    int test = 0;
                }
                Coordinates[] locations = new Coordinates[100];
                limiter++;
                for(int j = 0; j < words[i].length(); j++)  // will gather all of the coordinates for a word in this loop
                {
                    switch (direction){
                        case 1: //up
                            coordinateY++;
                            locations[j] = new Coordinates(coordinateX, coordinateY);

                            break;
                        case 2: //down
                            coordinateY--;
                            locations[j] = new Coordinates(coordinateX, coordinateY);

                            break;
                        case 3: //left
                            coordinateX--;
                            locations[j] = new Coordinates(coordinateX, coordinateY);

                            break;
                        case 4: //right
                            coordinateX++;
                            locations[j] = new Coordinates(coordinateX, coordinateY);

                            break;
                        case 5:  // up-right
                            coordinateY++;
                            coordinateX++;
                            locations[j] = new Coordinates(coordinateX, coordinateY);

                            break;
                        case 6: // up-left
                            coordinateY++;
                            coordinateX--;
                            locations[j] = new Coordinates(coordinateX, coordinateY);

                            break;
                        case 7: // down-left
                            coordinateY--;
                            coordinateX--;
                            locations[j] = new Coordinates(coordinateX, coordinateY);

                            break;
                        case 8: // down-right
                            coordinateY--;
                            coordinateX++;
                            locations[j] = new Coordinates(coordinateX, coordinateY);

                            break;
                        case 9: // no-move
                            locations[j] = new Coordinates(coordinateX, coordinateY);
                            break;
                    }
                }

                int length = 0;
                for(int k = 0; k< locations.length; k++){
                    if(locations[k] != null){
                        length++;
                    }
                }

                if(checkCoordinates(game, locations, words[i], length) && locations != null){
                    finalCoordinateCounter++;
                    gameWords[i] = words[i];

                    if(locations[0] == null){
                        int test = 0;
                        createGame(words,10,10);
                    }


                    //if(locations[length-1] == null){
                    //    int test = 0;
                    //    createGame(words,10,10);
//
                    //}


                    WordTracker tracker = new WordTracker(words[i], locations[0].a, locations[0].b, locations[length-1].a, locations[length-1].b);
                    finalCoordinates[finalCoordinateCounter] = tracker;
                    for(int k = 0; k< length; k++){
                        game = placeLetter(locations[k].a, locations[k].b, words[i], k, game);
                    }
                    response.Game = game;
                    if(finalCoordinates == null){
                        int test = 0;
                    }
                    response.WordDetails = finalCoordinates;
                }else{
                    //if checkCoordinates fails...
                    //try the word again with new coordinates
                    coordinateX = rand.nextInt((max - min) + 1) + min; // picks a random x
                    coordinateY = rand.nextInt((max - min) + 1) + min; // picks a random y
                    direction = rand.nextInt(directions.length) +1; // picks a random direction/
                    i--;
                }
            }

            for(int i=0; i<columns; i++){ // nested 4 loop to fill in empty spaces with letters
                for(int j=0; j<rows; j++){
                    Random r = new Random();
                    char c = (char)(r.nextInt(26) + 'a');
                    String s = String.valueOf(c).toUpperCase(); // we will only play the game with uppercase letters
                    if(game[i][j] == ".") game[i][j] = s; // only place it, if its not occupied already

                    String pos1 = String.valueOf(i);
                    String pos2 = String.valueOf(j);
                    System.out.print(game[i][j]); // for testing in a console window
                    System.out.print(" ");
                }
                System.out.println(); // go do next line
            }
        }catch(IllegalArgumentException ex)
        {
            return response;
        }

        for(int p = 0; p<= gameWords.length-1; p++) {
            System.out.println(gameWords[p]);
            if(gameWords[p] != null && gameWords[p] != "null") response.Words[p] = gameWords[p];
        }

        for (int i = 0; i < response.Words.length ; i++) {
            if(response.Words[i] == null){
                response.Words[i] = " ";
            }
        }
        return response;
    }

    public static String[][] placeLetter(int coordinateX, int coordinateY, String word, int iteration, String[][] game){  // method used to just place an individual letter of an individual word into the Game
        String[][] response = new String[game.length][game.length];
        try
        {
            if(coordinateX <0) throw new IllegalArgumentException("Location Null");
            if(coordinateY <0) throw new IllegalArgumentException("Location invalid");
            if(word == null) throw new IllegalArgumentException("Word Null");
            if(iteration < 0) throw new IllegalArgumentException("Illegal Iteration");

            String letter = Character.toString(word.charAt(iteration));
            game[coordinateX][coordinateY] = letter.toUpperCase();

            response = game;

        }catch(IllegalArgumentException ex)
        {
            response = null;
            return response;
        }
        return response;
    }

    public static boolean checkCoordinates(String[][] game, Coordinates[] locations, String word, int length){
        for(int i = 0; i <= length-1; i++){
            String letter = Character.toString(word.charAt(i));

            if(locations[i].a < (game.length) && locations[i].b < (game.length) && locations[i].a >= 1 && locations[i].b >= 1 )
            {} else return false;

            if((game[locations[i].a][locations[i].b] == ".") || (game[locations[i].a][locations[i].b] == letter.toUpperCase()))
            {} else return false;
        }
        return true;
    }
}
