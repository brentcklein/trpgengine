package rpg.classes;

public class Config {
    Integer start;
    Room[] rooms;

    public Config(Room[] rooms) {
        this.start = 1;
        this.rooms = rooms;
    }

    public Config(Integer start, Room[] rooms) {
        this.start = start;
        this.rooms = rooms;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }

    public Room getRoom(int idx) {
        return rooms[idx];
    }
}