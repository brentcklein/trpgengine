package rpg.classes;

import java.util.*;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		String filename = "config.json";

		if (args.length > 0 && args[0].equalsIgnoreCase("example")) {
			filename = "example.config.json";
		} else if (args.length > 0 && args[0].equalsIgnoreCase("simple")) {
			filename = "simple.example.config.json";
		}

		State s = WorldBuilder.getNewState(filename).get();

		while (!s.gameOver) {
			System.out.println(s.currentRoom.getDescription());
			Map<String,Integer> exits = s.currentRoom.getExits();

			for (String direction :
					exits.keySet()) {
				System.out.println("There is an exit to the " + direction.toUpperCase());
			}

			s.gameOver = s.currentRoom.isEnd();

			if (!s.gameOver) {
				String input = sc.nextLine();

				Optional<ActionSet> actionSetOptional = InputParser.parseInput(input);

				actionSetOptional.ifPresentOrElse(
					// How do we make sure we don't run into collisions between the names of items and features?
					actionSet -> {
						Optional<Feature> optionalFeature =
							s.currentRoom.getFeatures().stream().filter(
								(f) -> f.getName().equals(actionSet.getSubjectOptional().get())).findAny();
						Optional<Item> optionalItem = optionalFeature.filter((f) -> f instanceof Item).map((f) -> (Item)f);
						Optional<Item> optionalInventoryItem = s.player.getItem(actionSet.getSubjectOptional().get());
						// TODO: refactor to use reflection instead of switch?
						switch (actionSet.getVerbOptional().get()) {
							case "go":
							case "head":
							case "travel":

								actionSet.getSubjectOptional()
									.flatMap(s.currentRoom::getAdjacentRoom)
									.flatMap(s::getRoomOptional)
									.ifPresentOrElse(
										(d) -> d.goTo(actionSet, s),
										() -> System.out.println("There is not an exit in that direction."));
								break;
							case "look":
							case "examine":
								if (
									actionSet.getSubjectOptional().get().equalsIgnoreCase("room") ||
										actionSet.getSubjectOptional().get().equalsIgnoreCase("around")) {
									s.currentRoom.look(actionSet, s);
								} else {
									optionalFeature
										.or(() -> optionalInventoryItem)
										.ifPresentOrElse(
											o -> o.look(actionSet,s),
											() -> System.out.println("You don't see anything interesting."));
								}
								break;
							case "take":
								optionalItem.ifPresentOrElse(
									(item -> item.take(actionSet,s)),
									() -> System.out.println("You can't take that."));
								break;
							case "use":
								optionalInventoryItem.ifPresentOrElse(
									(item -> item.use(actionSet,s)),
									() -> System.out.println("You don't have a " + actionSet.getSubjectOptional().get() + "."));
								break;
							case "drop":
								optionalInventoryItem.ifPresentOrElse(
									(item -> item.drop(actionSet,s)),
									() -> System.out.println("You don't have a " + actionSet.getSubjectOptional().get() + "."));
								break;
							default:
								// How do we make sure we don't run into collisions between the names of items and features?
								optionalFeature.or(() -> optionalInventoryItem)
									.ifPresentOrElse(
										(feature -> feature.use(actionSet,s)),
										() -> System.out.println("You can't do that."));
								break;
						}
					},
					() -> System.out.println("You can't do that."));
			}
		}

		System.out.println("\n\n\nGame Over!");
		sc.close();
	}
}