/*
** Filename: Ship.java
** Project: Battleship
** By: Daniel Kato
*/
package edu.oregonstate.cs361.battleship;

/* Ship class */
public class Ship {
    public String name;
    public int length;
    public Coord start;
    public Coord end;

    /*
    ** Function Name: Ship (constructor)
    ** Parameters: None
    ** Description: Initializes Ship
    ** Return: None
    */
    public Ship() {
        name = "";
        length = 0;
        start = new Coord();
        end = new Coord();
    }

    /*
    ** Function Name: Ship (constructor)
    ** Parameters: String newName - name to be assigned to ship.name
    **             int newLength - number to be assinged to ship.length
    ** Description: Initializes Ship
    ** Return: None
    */
    public Ship(String newName, int newLength) {
        name = newName;
        length = newLength;
        start = new Coord();
        end = new Coord();
    }
}
