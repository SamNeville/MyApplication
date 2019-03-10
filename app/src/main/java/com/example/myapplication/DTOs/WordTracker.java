package com.example.myapplication.DTOs;
import java.lang.*;
import java.*;

public class WordTracker {

    public String word;
    public int beginX;
    public int beginY;
    public int endY;
    public int endX;

    // constructor
    public WordTracker(String w, int a, int b, int c, int d) {
        this.word = w;
        this.beginX = a;
        this.beginY = b;
        this.endX = c;
        this.endY = d;
    }

    // getter
    public int getBeginX() { return beginX; }
    public int getBeginY() { return beginY; }
    public int getEndX() { return endX; }
    public int getEndY() { return endY; }
    public String getWord() {return word; }
    // setter

    public void setBeginX(int a) { this.beginX = a; }
    public void setBeginY(int b) { this.beginY = b; }
    public void setEndX(int a) { this.endX = a; }
    public void setEndY(int b) { this.endY = b; }
    public void setWord(String w) {this.word = w; }
}