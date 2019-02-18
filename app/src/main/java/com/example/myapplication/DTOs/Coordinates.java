package com.example.myapplication.DTOs;
import java.lang.*;
import java.*;

public class Coordinates {
    public int a;
    public int b;

    // constructor
    public Coordinates(int a, int b) {
        this.a = a;
        this.b = b;
    }

    // getter
    public int getA() { return a; }
    public int getB() { return b; }
    // setter

    public void setA(int a) { this.a = a; }
    public void setB(int b) { this.b = b; }
}