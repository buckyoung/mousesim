public class Food {

	private static final double MIN_NUTRITION = 4.23;
	private static final double MAX_NUTRITION = 34.532;
	private final double NUTRITION;

	private static final int MAX_FOOD = 4;
	private static int curr_food = 0;


	private Food() {
		NUTRITION = MIN_NUTRITION+(MAX_NUTRITION-MIN_NUTRITION)*MouseSim.rand.nextDouble();
	}

	private static boolean canCreate() {
		return curr_food < MAX_FOOD;
	}

	private static Food create() {
		if(canCreate()) {
			curr_food++;
			return new Food();
		}

		return null;
	}

	public static void createRandom() {
		Food food = create();

		if(food != null) {
			MouseSim.getWorld().getRandomWorldNode().add((Food)food);
		}
	}

}