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
        BattleshipModel bsModel = new BattleshipModel();
        Gson gson = new Gson();

        // Convert from Java object to JSON
        String newModel = gson.toJson(bsModel, BattleshipModel.class);
        return newModel;
    }

    /*
    ** Function: private static BattleshipModel getModelFromReq
    ** Parameters: Request req - the HTML request object
    ** Description: This function should accept an HTTP request and deseralize it into an actual Java object.
    ** Return: Deserialized battleshipModel object sent by client
    */
    private static BattleshipModel getModelFromReq(Request req){
        Gson gson = new Gson();

        // Convert from JSON to java object
        BattleshipModel deserializedModel = gson.fromJson(req.body(), BattleshipModel.class);

        return deserializedModel;
    }

    /*
    ** Function: private static void placeShipAt
    ** Parameters: int length - length of chosen ship
    **             int xCoord - starting x coordinate of ship
    **             int yCoord - starting y coordinate of ship
    **             boolean direction - the orientation of the ship
    **             Ship userShip - Ship object that is to be changed
    ** Description: This function manipulates the game model based on the parameters in the url]
    ** Return: None
    */
    private static void placeShipAt(int length, int xCoord, int yCoord, boolean direction, Ship userShip)
    {
        if(direction)
        {
            userShip.start.Across = xCoord;
            userShip.start.Down = yCoord;
            userShip.end.Across = xCoord;
            userShip.start.Down = yCoord + length;
        }else
        {
            userShip.start.Across = xCoord;
            userShip.start.Down = yCoord;
            userShip.end.Across = xCoord + length;
            userShip.start.Down = yCoord;
        }

    }

    //This controller should take a json object from the front end, and place the ship as requested, and then return the object.
    private static String placeShip(Request req) {

        //get Json object that contains user coordinates
        BattleshipModel curModel = getModelFromReq(req);

        //get string-type information
        String type = req.params("type");                    //ship type
        String newOrientation = req.params("orientation");   //vert. or horiz.

        //set direction flag
        boolean direction = false;
        if(newOrientation == "vertical") direction = true;

        //take coordinates in as integers
        int xCoord = Integer.parseInt(req.params("row"));
        int yCoord = Integer.parseInt(req.params("col"));

        //get length of ship type from ship container
        int length, endCoord;
        if(type == "AircraftCarrier")
        {
            placeShipAt(curModel.aircraftCarrier.length, xCoord, yCoord, direction, curModel.aircraftCarrier);
        }else if(type == "Battleship")
        {
            placeShipAt(curModel.battleship.length, xCoord, yCoord, direction, curModel.battleship);
        }else if(type == "Cruiser")
        {
            placeShipAt(curModel.cruiser.length, xCoord, yCoord, direction, curModel.cruiser);
        }else if(type == "Destroyer")
        {
            placeShipAt(curModel.destroyer.length, xCoord, yCoord, direction, curModel.destroyer);
        }else if(type == "Submarine")
        {
            placeShipAt(curModel.submarine.length, xCoord, yCoord, direction, curModel.submarine);
        }
        return null;
    }

    //Similar to placeShip, but with firing.
    private static String fireAt(Request req) {
        //PSUEDO-CODE (like psuedo-science. ex: psychology)

        /*
        Take user inputted coordinates they will be firing at
        Compare it against positions of each ship - five ships so five comparisons
        If hit lands on position of ship, return message saying they got a hit
            send update to newModel to update the game board, showing damaged ship
        else the hit lands in the water, return message saying they missed
            send update to newModel to update board, showing missed spot
         */

        BattleshipModel recentModel = getModelFromReq(req);

        int row = Integer.parseInt(req.params("row"));                    //row of fire
        int col = Integer.parseInt(req.params("col"));                    //column of fire

        int startCoordAcross, startCoordDown, endCoordAcross, endCoordDown;     //starting/ending point of ships across (rows) and down(col)

        /*-----check aircraftCarrier-----
         * This process is the same for every ship model-----*/
        startCoordAcross = recentModel.aircraftCarrier.start.Across;            //x (row) start value
        endCoordAcross   = recentModel.aircraftCarrier.end.Across;              //x (row) end value

        Coord coord = new Coord();
        coord.setCoords(row, col);                                              //init Coord class

        for(int i = startCoordAcross; i <= endCoordAcross; i++){                //check x coord (row) against all x coord (row) of aircraftCarrier
            if(row == i){                                                       //if there is a match, check the y coordinates
                startCoordDown = recentModel.aircraftCarrier.start.Down;        //y (col) start value
                endCoordDown   = recentModel.aircraftCarrier.end.Down;          //y (col) end value

                for(int j = startCoordDown; j <= endCoordDown; j++){            //check y coord(col) against all y coord (col) of aircraftCarrier
                    if(col == j){                                               //if match, then that is a hit on the ship. Update playerHit array
                        recentModel.playerHits.add(coord);                      //append 1 elements to arrayList
                        //add x coord (row) to the playerHits arrayList to the last element

                        Gson gson = new Gson();                                              //return updated JSON file
                        String JsonModel = gson.toJson(recentModel, BattleshipModel.class);
                        System.out.println(JsonModel);
                        return JsonModel;
                    }
                    else{                                                       //if no match, it is a miss. Update playerMiss
                        recentModel.playerMisses.add(coord);                        //append 1 elements to arrayList

                        Gson gson = new Gson();                                              //return updated JSON file
                        String JsonModel = gson.toJson(recentModel, BattleshipModel.class);
                        System.out.println(JsonModel);
                        return JsonModel;

                    }
                }
            }
            else{                                                               //if no match, it is a miss. Move on to check next ship
                recentModel.playerMisses.add(coord);                                //append  element to arrayList

                Gson gson = new Gson();                                              //return updated JSON file
                String JsonModel = gson.toJson(recentModel, BattleshipModel.class);
                System.out.println(JsonModel);
                return JsonModel;
            }
        }

        /*-----check battleShip-----*/
        startCoordAcross = recentModel.battleship.start.Across;         //x (row) start value
        endCoordAcross   = recentModel.battleship.end.Across;           //x (row) end value

        for(int i = startCoordAcross; i <= endCoordAcross; i++){        //check x coord (row) against all x coord (row) of battleship
            if(row == i){                                               //if there is a match, check the y coordinates
                startCoordDown = recentModel.battleship.start.Down;     //y (col) start value
                endCoordDown   = recentModel.battleship.end.Down;       //y (col) end value

                for(int j = startCoordDown; j <= endCoordDown; j++){    //check y coord(col) against all y coord (col) of battleship
                    if(col == j){                                       //if match, then that is a hit on the ship
                        recentModel.playerHits.add(coord);                  //append 1 element to arrayList

                        Gson gson = new Gson();                         //return updated JSON file
                        String JsonModel = gson.toJson(recentModel, BattleshipModel.class);
                        System.out.println(JsonModel);
                        return JsonModel;
                    }
                    else{                                                 //if no match, it is a miss. Move on to check next ship
                        recentModel.playerMisses.add(coord);                  //append 1 elements to arrayList

                        Gson gson = new Gson();                                              //return updated JSON file
                        String JsonModel = gson.toJson(recentModel, BattleshipModel.class);
                        System.out.println(JsonModel);
                        return JsonModel;
                    }
                }
            }
            else{                                                       //if no match, it is a miss. Move on to check next ship
                recentModel.playerMisses.add(coord);                        //append 1 elements to arrayList

                Gson gson = new Gson();                                              //return updated JSON file
                String JsonModel = gson.toJson(recentModel, BattleshipModel.class);
                System.out.println(JsonModel);
                return JsonModel;
            }
        }
        /*-----check cruiser-----*/
        startCoordAcross = recentModel.cruiser.start.Across;            //x (row) start value
        endCoordAcross   = recentModel.cruiser.end.Across;              //x (row) end value

        for(int i = startCoordAcross; i <= endCoordAcross; i++){        //check x coord (row) against all x coord (row) of cruiser
            if(row == i){                                               //if there is a match, check the y coordinates
                startCoordDown = recentModel.cruiser.start.Down;        //y (col) start value
                endCoordDown   = recentModel.cruiser.end.Down;          //y (col) end value

                for(int j = startCoordDown; j <= endCoordDown; j++){    //check y coord(col) against all y coord (col) of cruiser
                    if(col == j){                                       //if match, then that is a hit on the ship
                        recentModel.playerHits.add(coord);                  //append 1 elements to arrayList

                        Gson gson = new Gson();                             //return updated JSON file
                        String JsonModel = gson.toJson(recentModel, BattleshipModel.class);
                        System.out.println(JsonModel);
                        return JsonModel;
                    }
                    else{                                               //if no match, it is a miss. Move on to check next ship
                        recentModel.playerMisses.add(coord);                //append 1 elements to arrayList

                        Gson gson = new Gson();                                              //return updated JSON file
                        String JsonModel = gson.toJson(recentModel, BattleshipModel.class);
                        System.out.println(JsonModel);
                        return JsonModel;
                    }
                }
            }
            else{                                                       //if no match, it is a miss. Move on to check next ship
                recentModel.playerMisses.add(coord);                        //append 1 elements to arrayList

                Gson gson = new Gson();                                              //return updated JSON file
                String JsonModel = gson.toJson(recentModel, BattleshipModel.class);
                System.out.println(JsonModel);
                return JsonModel;
            }
        }

        /*-----check destroyer-----*/
        startCoordAcross = recentModel.destroyer.start.Across;          //x (row) start value
        endCoordAcross   = recentModel.destroyer.end.Across;            //x (row) end value

        for(int i = startCoordAcross; i <= endCoordAcross; i++){        //check x coord (row) against all x coord (row) of destroyer
            if(row == i){                                               //if there is a match, check the y coordinates
                startCoordDown = recentModel.destroyer.start.Down;      //y (col) start value
                endCoordDown   = recentModel.destroyer.end.Down;        //y (col) end value

                for(int j = startCoordDown; j <= endCoordDown; j++){    //check y coord(col) against all y coord (col) of destroyer
                    if(col == j){                                       //if match, then that is a hit on the ship
                        recentModel.playerHits.add(coord);                  //append 1 elements to arrayList

                        Gson gson = new Gson();                          //return updated JSON file
                        String JsonModel = gson.toJson(recentModel, BattleshipModel.class);
                        System.out.println(JsonModel);
                        return JsonModel;
                    }
                    else{                                                //if no match, it is a miss. Move on to check next ship
                        recentModel.playerMisses.add(coord);                 //append 1 elements to arrayList

                        Gson gson = new Gson();                                              //return updated JSON file
                        String JsonModel = gson.toJson(recentModel, BattleshipModel.class);
                        System.out.println(JsonModel);
                        return JsonModel;
                    }
                }
            }
            else{                                                        //if no match, it is a miss. Move on to check next ship
                recentModel.playerMisses.add(coord);                        //append 1 elements to arrayList

                Gson gson = new Gson();                                              //return updated JSON file
                String JsonModel = gson.toJson(recentModel, BattleshipModel.class);
                System.out.println(JsonModel);
                return JsonModel;
            }
        }

        /*-----check submarine-----*/
        startCoordAcross = recentModel.submarine.start.Across;           //x (row) start value
        endCoordAcross   = recentModel.submarine.end.Across;             //x (row) end value

        for(int i = startCoordAcross; i <= endCoordAcross; i++){        //check x coord (row) against all x coord (row) of submarine
            if(row == i){                                               //if there is a match, check the y coordinates
                startCoordDown = recentModel.submarine.start.Down;      //y (col) start value
                endCoordDown   = recentModel.submarine.end.Down;        //y (col) end value

                for(int j = startCoordDown; j <= endCoordDown; j++){    //check y coord(col) against all y coord (col) of submarine
                    if(col == j){                                       //if match, then that is a hit on the ship
                        recentModel.playerHits.add(coord);                  //append 1 elements to arrayList

                        Gson gson = new Gson();                          //return updated JSON file
                        String JsonModel = gson.toJson(recentModel, BattleshipModel.class);
                        System.out.println(JsonModel);
                        return JsonModel;
                    }
                    else{                                                //if no match, it is a miss. Move on to check next ship
                        recentModel.playerMisses.add(coord);                 //append 1 elements to arrayList

                        Gson gson = new Gson();                                              //return updated JSON file
                        String JsonModel = gson.toJson(recentModel, BattleshipModel.class);
                        System.out.println(JsonModel);
                        return JsonModel;
                    }
                }
            }
            else{                                                        //if no match, it is a miss. Move on to check next ship
                recentModel.playerMisses.add(coord);                         //append 1 elements to arrayList

                Gson gson = new Gson();                                              //return updated JSON file
                String JsonModel = gson.toJson(recentModel, BattleshipModel.class);
                System.out.println(JsonModel);
                return JsonModel;

            }
        }

        return null;
    }
}
