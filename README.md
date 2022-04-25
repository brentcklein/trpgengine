# trpgengine
Java engine for a text-based rpg.

# Usage
## Running the game
1. build the project
2. run the project with `java rpg.classes.main`. Adding `simple` or `example` will load one of two example dungeons.
## Playing the game
1. use `look <object>` to look at an object in game, or `look` to look at your surroundings
2. use `go <direction>` to move around the dungeon. Note that you have to use the full names of each direction (eg `east`, `west`, `up`, `down`, etc).
# Developing
The layout of the dungeon is defined in `config/config.json`. This file lets you specify names, descriptions, and exits for each room, as well as features within each room. Room exits are defined using room `id`s, and more complex behavior can be accomplished by specifying a `CustomClass` for a room or feature. The value of `CustomClass` should match a class in `src/custom/`. `simple.example.config.json` and `example.config.json` are provided for reference, along with relevant custom classes.
