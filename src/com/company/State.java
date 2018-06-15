package com.company;

public class State {
    public Room currentRoom;
    public boolean gameOver = false;
    public Player player = new Player();

    public State(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
}
