package rpg.classes;

import java.util.*;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		String filename = "config.json";

		if (args.length > 0 && args[0].equalsIgnoreCase("example")) {
			filename = "example.config.json";
		} else if (args.length > 0 && args[0].equalsIgnoreCase("simple")) {
			filename = "simple.example.config.json";
		}

		State state = WorldBuilder.getNewState(filename).get();

		while (!state.gameOver) {
			System.out.println(state.currentRoom.getDescription());
			Map<String, Integer> exits = state.currentRoom.getExits();

			for (String direction : exits.keySet()) {
				System.out.println("There is an exit to the " + direction.toUpperCase());
			}

			state.gameOver = state.currentRoom.isEnd();

			if (!state.gameOver) {
				String input = scanner.nextLine();

				Optional<ActionSet> actionSetOptional = InputParser.parseInput(input);

				actionSetOptional.ifPresentOrElse(
						// How do we make sure we don't run into collisions between the names of items
						// and features?
						actionSet -> {
							Optional<Feature> optionalFeature = state.currentRoom.getFeatures().stream().filter(
									(f) -> f.getName().equals(actionSet.getSubjectOptional().get())).findAny();
							Optional<Item> optionalItem = optionalFeature.filter((f) -> f instanceof Item).map((f) -> (Item) f);
							Optional<Item> optionalInventoryItem = state.player.getItem(actionSet.getSubjectOptional().get());
							// TODO: refactor to use reflection instead of switch?
							switch (actionSet.getVerbOptional().get()) {
								case "go":
								case "head":
								case "travel":

									actionSet.getSubjectOptional()
											.flatMap(state.currentRoom::getAdjacentRoom)
											.flatMap(state::getRoomOptional)
											.ifPresentOrElse(
													(d) -> d.goTo(actionSet, state),
													() -> System.out.println("There is not an exit in that direction."));
									break;
								case "look":
								case "examine":
									if (actionSet.getSubjectOptional().get().equalsIgnoreCase("room") ||
											actionSet.getSubjectOptional().get().equalsIgnoreCase("around")) {
										state.currentRoom.look(actionSet, state);
									} else if (actionSet.getSubjectOptional().get().equalsIgnoreCase("inventory")) {
										Set<Item> inventory = state.player.getInventory();
										if (inventory.size() > 0) {
											Iterator<Item> inventoryIterator = inventory.iterator();
											while (inventoryIterator.hasNext()) {
												System.out.println(inventoryIterator.next().name);
											}
										} else {
											System.out.println("There is nothing in your inventory.");
										}
									} else {
										optionalFeature
												.or(() -> optionalInventoryItem)
												.ifPresentOrElse(
														o -> o.look(actionSet, state),
														() -> System.out.println("You don't see anything interesting."));
									}
									break;
								case "take":
									optionalItem.ifPresentOrElse(
											(item -> item.take(actionSet, state)),
											() -> System.out.println("You can't take that."));
									break;
								case "use":
									optionalInventoryItem.ifPresentOrElse(
											(item -> item.use(actionSet, state)),
											() -> System.out.println("You don't have a " + actionSet.getSubjectOptional().get() + "."));
									break;
								case "drop":
									optionalInventoryItem.ifPresentOrElse(
											(item -> item.drop(actionSet, state)),
											() -> System.out.println("You don't have a " + actionSet.getSubjectOptional().get() + "."));
									break;
								default:
									// How do we make sure we don't run into collisions between the names of items
									// and features?
									optionalFeature.or(() -> optionalInventoryItem)
											.ifPresentOrElse(
													(feature -> feature.use(actionSet, state)),
													() -> System.out.println("You can't do that."));
									break;
							}
						},
						() -> System.out.println("You can't do that."));
			}
		}

		System.out.println("\n\n\nGame Over!");
		scanner.close();
	}
}