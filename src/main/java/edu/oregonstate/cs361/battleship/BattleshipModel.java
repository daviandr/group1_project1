/*
** Filename: BattleshipModel.java
** Project: Battleship
** By: Group 1
*/
package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;

/* BattleshipModel class */
public class BattleshipModel {
    public Ship aircraftCarrier;
    public Ship battleship;
    public Ship cruiser;
    public Ship destroyer;
    public Ship submarine;
    public Ship computer_aircraftCarrier;
    public Ship computer_battleship;
    public Ship computer_cruiser;
    public Ship computer_destroyer;
    public Ship computer_submarine;
    public ArrayList<Coord> playerHits;         //store coordinates from user hits here. i.e., "user hit at [2][2], [2][3]..."
    public ArrayList<Coord> playerMisses;
    public ArrayList<Coord> computerHits;
    public ArrayList<Coord> computerMisses;

    /*
    ** Function Name: BattleshipModel (constructor)
    ** Parameters: None
    ** Description: Initializes BattleshipModel
    ** Return: None
    */
    public BattleshipModel(){
        InitUserShips();
        InitComputerShips();
        InitLists();
    }

    /*
    ** Function Name: InitUserShips
    ** Parameters: None
    ** Description: Initializes aircraftCarrier, battleship, cruiser, destroyer, and submarine;
    ** Return: None
    */
    public void InitUserShips(){
        aircraftCarrier = new Ship("AircraftCarrier", 5);
        battleship = new Ship("Battleship", 4);
        cruiser = new Ship("Cruiser", 3);
        destroyer = new Ship("Destroyer", 2);
        submarine = new Ship("Submarine", 2);
        playerHits = new ArrayList<Coord>();
        playerMisses = new ArrayList<Coord>();
    }

    /*
    ** Function Name: InitComputerShips
    ** Parameters: None
    ** Description: Initializes computer_aircraftCarrier, computer_battleship, computer_cruiser, computer_destroyer, and computer_submarine;
    ** Return: None
    */
    public void InitComputerShips(){
        computer_aircraftCarrier = new Ship("AircraftCarrier", 5);
        computer_aircraftCarrier.start.setCoords(2, 2);
        computer_aircraftCarrier.end.setCoords(2, 7);
        computer_battleship = new Ship("Battleship", 4);
        computer_battleship.start.setCoords(2, 8);
        computer_battleship.end.setCoords(6, 8);
        computer_cruiser = new Ship("Cruiser", 3);
        computer_cruiser.start.setCoords(4, 1);
        computer_cruiser.end.setCoords(4, 4);
        computer_destroyer = new Ship("Destroyer", 2);
        computer_destroyer.start.setCoords(7, 3);
        computer_destroyer.end.setCoords(7, 5);
        computer_submarine = new Ship("Submarine", 2);
        computer_submarine.start.setCoords(9, 6);
        computer_submarine.end.setCoords(9, 8);
    }

    /*
    ** Function Name: InitLists
    ** Parameters: None
    ** Description: The game board is 10x10, and the sum of all the ship's lengths are 16, therefore a user can have a maximum of 16 hits, and 84 misses
    ** Return: None
    */
    public void InitLists(){
        playerHits = new ArrayList<Coord>();
        playerMisses = new ArrayList<Coord>();
        computerHits = new ArrayList<Coord>();
        computerMisses = new ArrayList<Coord>();
    }

    /*
    ** Function Name: generateCoordinates
    ** Parameters: None
    ** Description: Generates random coordinates for the computerHits
    **              ships
    ** Return: randomCoordinates
    */

    public Coord[] generateCoordinates(){
        // TODO: Generate random coordinates
        return null;
    }

}
