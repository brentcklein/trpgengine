package rpg.classes;

import java.util.Arrays;
import java.util.Optional;

public class State {
    public Room currentRoom;
    public boolean gameOver = false;
    public Player player = new Player();
    private Room[] rooms;

    public State(Integer startingRoom, Room[] rooms) throws IllegalArgumentException {
        this.rooms = rooms;
        getRoomOptional(startingRoom)
                .ifPresentOrElse(
                        room -> this.currentRoom = room,
                        () -> {throw new IllegalArgumentException("Starting room does not exist!");}
                );
    }

    public Optional<Room> getRoomOptional(Integer id) {
        Room[] matches = Arrays.stream(rooms).filter(
                (Room room) -> room.getId().equals(id)
        ).toArray(Room[]::new);

        if (matches.length > 0) {
            return Optional.of(matches[0]);
        } else {
            return Optional.empty();
        }
    }
}
