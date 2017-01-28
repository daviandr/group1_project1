/*
** Filename: Coord.java
** Project: Battleship
** By: Group 1
*/
package edu.oregonstate.cs361.battleship;

/* Coord class */
 public class Coord {
     public int Across;
     public int Down;

     /*
     ** Function Name: Coord (constructor)
     ** Parameters: None
     ** Description: Initializes Coord
     ** Return: None
     */
     public Coord() {
         Across = 0;
         Down = 0;
     }

     /*
     ** Function Name: setCoords
     ** Parameters: int newAcross - across value for ship
     **             int newDown - down value for ship
     ** Description: Initializes Ship
     ** Return: None
     */
     public void setCoords(int newAcross, int newDown) {
         Across = newAcross;
         Down = newDown;
     }
 }
