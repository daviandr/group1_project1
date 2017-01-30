/*
** Filename: Main.java
** Project: Battleship
** By: Group 1
*/
package edu.oregonstate.cs361.battleship;

import java.lang.reflect.Field;
import com.google.gson.Gson;
import spark.Request;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

public class Main {

    public static void main(String[] args) {
        //This will allow us to server the static pages such as index.html, app.js, etc.
        staticFiles.location("/public");

        //This will listen to GET requests to /model and return a clean new model
        get("/model", (req, res) -> newModel());

        //This will listen to POST requests and expects to receive a game model, as well as location to fire to
        post("/fire/:row/:col", (req, res) -> fireAt(req));

        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:id/:row/:col/:orientation", (req, res) -> placeShip(req));
    }

    //This function should return a new model
    private static String newModel() {
        return null;
    }

    //This function should accept an HTTP request and deseralize it into an actual Java object.
    private static BattleshipModel getModelFromReq(Request req){
        return null;
    }

    //This controller should take a json object from the front end, and place the ship as requested, and then return the object.
    private static String placeShip(Request req) {
        return null;
    }

    //Similar to placeShip, but with firing.
    private static String fireAt(Request req) {
        //PSUEDO-CODE

        /*
        Take user inputted coordinates they will be firing at
        Compare it against positions of each ship - five ships so five comparisons
        If hit lands on position of ship, return message saying they got a hit
            send update to newModel to update the game board, showing damaged ship
        else the hit lands in the water, return message saying they missed
            send update to newModel to update board, showing missed spot
        
         */
        return null;
    }

}
//comment