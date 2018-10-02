package rpg.classes;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Room start = new Room(
                "You're in a room made of stone.",
                "Light is coming through a hole in the ceiling. To the west a flight of stairs leads " +
                        "downward."
        );
        Room stairsDown = new Room(
                "You are on the landing.",
                "There is a landing after about fifty steps. To the north the stairs continue " +
                    "down; to the east the stairs continue up."
        );
        Room closet = new Room(
                "You are in a closet.",
                "You're in a small closet. From the looks of things it was a storage room at one point " +
                        "but hasn't be used in some time."
        );
        Room dungeon = new Room(
                "You're in what appears to be a castle dungeon.",
                "Chains hang from the walls, and there are several small cells with mostly rotten doors."
        );
        Room cell1 = new Room(
                "You are in one of the cells.",
                "Manacles hang from a rusty peg in the wall."
        );
        Room cell2 = new Room(
                "You are in one of the cells.",
                "The broken remains of a wooden cot stand against one wall."
        );
        Room oubliette = new Room(
                "You are in an oubliette.",
                "Faint light is coming through a hole in the ceiling. You scream, but no one hears you..."
        ) {
            @Override
            public void goTo(ActionSet actionSet, State s) {
                if (!s.player.getItem("kazoo").isPresent()) {
                    setEnd(true);
                    System.out.println(getDetailedDescription());
                } else {
                    setDetailedDescription("Faint torchlight is coming through a rough hole in the ceiling. There are " +
                            "no other openings.");
                }
                super.goTo(actionSet, s);
            }
        };
        Room cell3 = new Room(
                "You step into one of the cells, but as you do you hear a distinct 'click.' The floor gives " +
                        "way underneath your feet, and you fall a dozen feet into a pit.\n",
                ""
        ) {
            @Override
            public void goTo(ActionSet actionSet, State s) {
                if (!isVisited()) {
                    setVisited(true);
                    System.out.println(getDescription());
                    oubliette.goTo(actionSet, s);
                } else {
                    setDescription("You are in one of the cells.");
                    setDetailedDescription("There's a rickety table on one side of the cell. A large hole opens in the " +
                            "middle of the floor.");
                    super.goTo(actionSet, s);
                }
            }
        };
        Room cavern = new Room(
                "You are in a large cavern.",
                "Crystals embedded in the rock around you glow a soft purple, illuminating the area. " +
                        "Several tunnels branch off from the main cavern."
        );
        Room cave1 = new Room(
                "You are in a cave.",
                "A large stalagmite fills one corner of the room."
        ) {
            @Override
            public void look(ActionSet actionSet, State s) {
                if (!(s.player.getItem("torch").isPresent() && s.player.getItem("torch").get().isActivated())) {
                    System.out.println("It's dark.");
                } else {
                    super.look(actionSet, s);
                }
            }
        };
        Room cave2 = new Room(
                "You are in a cave.",
                "You hear water dripping from somewhere up above."
        ) {
            @Override
            public void look(ActionSet actionSet, State s) {
                if (!(s.player.getItem("torch").isPresent() && s.player.getItem("torch").get().isActivated())) {
                    System.out.println("It's dark.");
                } else {
                    super.look(actionSet, s);
                }
            }
        };
        Room cave3 = new Room(
                "You are in a cave.",
                "A small stream splashes out of a fissure in the cave wall, then disappears down a " +
                        "crack at the base of a stalagmite."
        ) {
            @Override
            public void look(ActionSet actionSet, State s) {
                if (!(s.player.getItem("torch").isPresent() && s.player.getItem("torch").get().isActivated())) {
                    System.out.println("It's dark.");
                } else {
                    super.look(actionSet, s);
                }
            }
        };
        Room cave4 = new Room(
                "You are in a cave.",
                "Stalagmites and stalactites cover the floor and ceiling. The sound of dripping water " +
                        "is constant in this room."
        ) {
            @Override
            public void look(ActionSet actionSet, State s) {
                if (!(s.player.getItem("torch").isPresent() && s.player.getItem("torch").get().isActivated())) {
                    System.out.println("It's dark.");
                } else {
                    super.look(actionSet, s);
                }
            }
        };
        Room cave5 = new Room(
                "You are in a cave.",
                "The floor is sandy here."
        ) {
            @Override
            public void look(ActionSet actionSet, State s) {
                if (!(s.player.getItem("torch").isPresent() && s.player.getItem("torch").get().isActivated())) {
                    System.out.println("It's dark.");
                } else {
                    super.look(actionSet, s);
                }
            }
        };
        Room cave6 = new Room(
                "You are in a cave.",
                "To the south you see worked stone peeking through the living rock in places."
        ) {
            @Override
            public void look(ActionSet actionSet, State s) {
                if (!(s.player.getItem("torch").isPresent() && s.player.getItem("torch").get().isActivated())) {
                    System.out.println("It's dark.");
                } else {
                    super.look(actionSet, s);
                }
            }
        };
        Room shrine = new Room(
                "You are in some kind of shrine.",
                "The room is brightly lit with glowing purple crystals."
        );

        start.addExits(Map.of("WEST", stairsDown));
        start.addFeature(new Item(
                "kazoo",
                "There is a kazoo on the floor.",
                "A red kazoo. It's a little dusty."
        ) {
            @Override
            public void use(ActionSet actionSet, State s) {
                System.out.println("doot");
                s.currentRoom = start;
                if (!isActivated()) {
                    setActivated(true);
                } else {
                    System.out.println("The kazoo broke!");
                    s.player.removeItem(this);
                }
            }
        });
        start.addFeature(new Feature(
                "door",
                "There's a door to the east.",
                "A sturdy wooden door, banded with iron. It's locked."
        ) {
            @Override
            public void use(ActionSet actionSet, State s) {
                if (actionSet.getVerbOptional().get().equals("unlock")) {
                    Optional<Item> keyOptional = s.player.getItem("key");
                    if (keyOptional.isPresent()) {
                        this.useWith(keyOptional.get(), actionSet, s);
                    } else {
                        System.out.println("You don't have anything to unlock it with.");
                    }
                } else {
                    super.use(actionSet, s);
                }
            }

            @Override
            public void useWith(Item item, ActionSet actionSet, State s) {
                if (item.getName().equals("key")) {
                    System.out.println("You slide the key into the lock and turn it. There's an audible click, and the " +
                            "door swings open.");
                    start.addExits(Map.of("EAST", closet));
                    start.removeFeature(this);
                    s.currentRoom.setDetailedDescription("Light is coming through a hole in the ceiling. To the west a " +
                            "flight of stairs leads downward. To the east there's an open door.");
                } else {
                    super.useWith(item, actionSet, s);
                }
            }
        });
        stairsDown.addExits(Map.of("NORTH", dungeon, "EAST", start));
        stairsDown.addFeature(new Feature(
                "brazier",
                "The landing is lit by a brazier on the wall.",
                "A lit brazier with an open flame. Watch it--it's hot."
        ) {
            @Override
            public void useWith(Item item, ActionSet actionSet, State s) {
                if (item.getName().equals("torch")) {
                    System.out.println("You light the torch in the brazier.");
                    item.setActivated(true);
                } else {
                    super.useWith(item, actionSet, s);
                }
            }
        });
        closet.addExits(Map.of("WEST", start));
        closet.addFeature(new Item(
                "torch",
                "There's a torch on a shelf.",
                "It's an unlit torch."
        ) {
            @Override
            public void use(ActionSet actionSet, State s) {
                if (actionSet.getVerbOptional().get().equals("light")) {
                    if (isActivated()) {
                        System.out.println("It's already lit.");
                    } else {
                        Optional<Feature> brazierOptional = s.currentRoom.getFeature("brazier");
                        if (brazierOptional.isPresent()) {
                            brazierOptional.get().useWith(this, actionSet, s);
                        } else {
                            System.out.println("You don't have anything to light it with.");
                        }
                    }
                } else {
                    Optional<String> objectOptional = actionSet.getObjectOptional();
                    if (objectOptional.isPresent()) {
                        this.useOn(actionSet, s);
                    } else {
                        super.use(actionSet, s);
                    }
                }
            }

            @Override
            public void look(ActionSet actionSet, State s) {
                if (!isActivated()) {
                    super.look(actionSet, s);
                } else {
                    System.out.println("The lit torch gives off bright light.");
                }
            }
        });

        dungeon.addExits(Map.of("WEST", cell1, "NORTH", cell2, "EAST", cell3, "SOUTH", stairsDown));

        cell1.addExits(Map.of("EAST", dungeon));
        cell1.addFeature(new Feature(
                "rags",
                "There's a pile of rags in the corner.",
                "An old pile of rags, probably once used as a bed. There's a key underneath."
        ) {
            @Override
            public void look(ActionSet a, State s) {
                super.look(a, s);
                this.setDetailedDescription("An old pile of rags, probably once used as a bed.");
                if (!this.isActivated()) {
                    s.currentRoom.addFeature(new Item(
                            "key",
                            "There's a key on the floor.",
                            "A small key."
                    ) {
                        @Override
                        public void use(ActionSet actionSet, State s) {
                            if (actionSet.getObjectOptional().isPresent()) {
                                this.useOn(actionSet, s);
                            } else {
                                super.use(actionSet, s);
                            }
                        }
                    });
                    this.setActivated(true);
                }
            }
        });
        cell2.addExits(Map.of("SOUTH", dungeon));
        cell2.addFeature(new Feature(
                "obstacle",
                "An obstacle sits against the far wall.",
                "It's an obstacle. You should probably try to move it."
        ) {
            @Override
            public void use(ActionSet actionSet, State s) {
                if (
                    actionSet.getVerbOptional().get().equals("move") ||
                    actionSet.getVerbOptional().get().equals("push")
                ) {
                    s.currentRoom.addExits(Map.of("NORTH", cavern));
                    System.out.println("You move the obstacle to the side.");
                    setDetailedDescription("It's not really much of an obstacle anymore.");
                    s.currentRoom.setDetailedDescription("The broken remains of a wooden cot stand against one wall. " +
                            "There's a small hole in the far wall you could probably crawl through.");
                } else {
                    super.use(actionSet, s);
                }
            }
        });
        cell3.addExits(Map.of("WEST", dungeon));
        cell3.addFeature(new Item(
                "crystal",
                "There's a crystal on the table.",
                "A small purple crystal. The soft glow seems to pulse slowly."
        ) {
            @Override
            public void drop(ActionSet actionSet, State s) {
                System.out.println("You drop the crystal and it shatters on the ground.");
                s.player.removeItem(this);
            }

            @Override
            public void use(ActionSet actionSet, State s) {
                if (
                    (actionSet.getVerbOptional().get().equals("use") ||
                        actionSet.getVerbOptional().get().equals("put") ||
                        actionSet.getVerbOptional().get().equals("place") ||
                        actionSet.getVerbOptional().get().equals("set")) &&
                    actionSet.getObjectOptional().isPresent())
                {
                    this.useOn(actionSet, s);
                } else {
                    super.use(actionSet, s);
                }
            }
        });

        cavern.addExits(Map.of("EAST", cave6, "SOUTH", cell2, "WEST", cave1));
        cave1.addExits(Map.of("NORTH", cave2, "EAST", cavern,"WEST", cavern));
        cave2.addExits(Map.of("NORTH", cave6, "EAST", cave3, "SOUTH", cave2, "WEST", cavern));
        cave3.addExits(Map.of("NORTH", cave4, "EAST", cavern, "WEST", cave2));
        cave4.addExits(Map.of("NORTH", shrine, "EAST", cavern, "SOUTH", cave3, "WEST", cave5));
        cave5.addExits(Map.of("EAST", cave6, "SOUTH", cavern));
        cave6.addExits(Map.of("NORTH", cave5, "EAST", cave2));

        shrine.addExits(Map.of("SOUTH", cave4));
        shrine.addFeature(new Feature(
                "pedestal",
                "There is a pedestal in the middle of the room.",
                "A pedestal formed from a cluster of glowing purple crystals. It looks like something " +
                        "small is meant to be placed on top."
        ) {
            @Override
            public void useWith(Item item, ActionSet actionSet, State s) {
                if (item.getName().equals("crystal")) {
                    System.out.println("You place the crystal on to the pedestal. As soon as you do, the crystals in the " +
                            "cave begin to pulse in time with the one on the pedestal. Riches are yours! The dragon is " +
                            "slain! The laundry is done! You win!");
                    s.currentRoom.setEnd(true);
                    s.currentRoom.setDescription("");
                    s.currentRoom.setDetailedDescription("");
                    s.currentRoom.removeExit("SOUTH");
                } else {
                    super.useWith(item, actionSet, s);
                }
            }
        });

        State s = new State(start);

        while (!s.gameOver) {
            System.out.println(s.currentRoom.getDescription());
            Map<String,Room> exits = s.currentRoom.getExits();

            for (String direction :
                    exits.keySet()) {
                System.out.println("There is an exit to the " + direction);
            }

            s.gameOver = s.currentRoom.isEnd();

            if (!s.gameOver) {
                String input = sc.nextLine();

                Optional<ActionSet> actionSetOptional = InputParser.parseInput(input);

                if (actionSetOptional.isPresent()) {
                    ActionSet actionSet = actionSetOptional.get();
                    Optional<Feature> optionalFeature =
                            s.currentRoom.getFeatures().stream().filter(
                                    (f) -> f.getName().equals(actionSet.getSubjectOptional().get())).findAny();
                    Optional<Item> optionalItem = optionalFeature.filter((f) -> f instanceof Item).map((f) -> (Item)f);
                    Optional<Item> optionalInventoryItem = s.player.getItem(actionSet.getSubjectOptional().get());
                    switch (actionSet.getVerbOptional().get()) {
                        case "go":
                        case "head":
                        case "travel":
                            Optional<Room> destination = s.currentRoom.getAdjacentRoom(actionSet.getSubjectOptional().get());

                            if (destination.isPresent()) {
                                destination.get().goTo(actionSet, s);
                            } else {
                                System.out.println("There is not an exit in that direction.");
                            }
                            break;
                        case "look":
                        case "examine":
                            if (
                                    actionSet.getSubjectOptional().get().equalsIgnoreCase("room") ||
                                            actionSet.getSubjectOptional().get().equalsIgnoreCase("around")) {
                                s.currentRoom.look(actionSet, s);
                            } else {
                                if (optionalFeature.isPresent()) {
                                    optionalFeature.get().look(actionSet, s);
                                } else if (optionalInventoryItem.isPresent()) {
                                    optionalInventoryItem.get().look(actionSet, s);
                                } else {
                                    System.out.println("You don't see anything interesting.");
                                }
                            }
                            break;
                        case "take":
                            if (optionalItem.isPresent()) {
                                optionalItem.get().take(actionSet, s);
                            } else {
                                System.out.println("You can't take that.");
                            }
                            break;
                        case "use":
                            if (optionalInventoryItem.isPresent()) {
                                optionalInventoryItem.get().use(actionSet, s);
                            } else {
                                System.out.println("You don't have a " + actionSet.getSubjectOptional().get());
                            }
                            break;
                        case "drop":
                            if (optionalInventoryItem.isPresent()) {
                                optionalInventoryItem.get().drop(actionSet, s);
                            } else {
                                System.out.println("You don't have a " + actionSet.getSubjectOptional().get() + ".");
                            }
                            break;
                        default:
                            // How do we make sure we don't run into collisions between the names of items and features?
                            if (optionalFeature.isPresent()) {
                                optionalFeature.get().use(actionSet, s);
                            } else if (optionalInventoryItem.isPresent()) {
                                optionalInventoryItem.get().use(actionSet, s);
                            } else {
                                System.out.println("You can't do that.");
                            }
                            break;
                    }
                } else {
                    System.out.println("You can't do that.");
                }
            }
        }

        System.out.println("\n\n\nGame Over!");
    }
}