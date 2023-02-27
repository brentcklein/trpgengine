# trpgengine
Java engine for a text-based rpg.
## Run
### Option 1: Using IntelliJ
1. Modify the run configuration for `rpg.classes.Main` to include the command line argument `example`.
2. Run `rpg.classes.Main` from IntelliJ to run the example dungeon.
### Option 2: Manually
Compile with Maven:
```shell
mvn clean compile assembly:single
```
Run the compiled jar with the argument `example` to run the example dungeon:
```shell
java -jar target/TextBasedRPG-1.0-SNAPSHOT-jar-with-dependencies.jar "example"
```
## Gameplay

### Basic Commands
- look/examine
- go/head/travel
- take
- use
- drop

To use one object on another, the basic construction is `use <item> on <object>`, but feel free to experiment to get the outcome you want!

As a shorthand, you can travel to an adjacent room by typing only the direction, eg: `east` is the same as `go east`.

### Example Dungeon
Explore your surroundings and reach the game-over screen! Make sure to `look` around each room for clues.