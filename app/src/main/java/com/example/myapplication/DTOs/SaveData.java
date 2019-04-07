package com.example.myapplication.DTOs;

public class SaveData {
    public String [][] game;
    public WordTracker[] allWords;
    public String [] wordsLeft;


    // getter
    public String[][] getGame() { return game; }
    public WordTracker[] getAllWords() { return allWords; }
    public String [] getWordsLeft() {return wordsLeft;}
}