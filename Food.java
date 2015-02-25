public class Food {

	private static final double MIN_NUTRITION = 4.23;
	private static final double MAX_NUTRITION = 34.532;
	public final double CALORIES; //Redo so a mouse can eat a bit of the food [make not final]

	private static final int MAX_FOOD = 12;
	private static int food_in_existance = 0;


	private Food() {
		CALORIES = MIN_NUTRITION+(MAX_NUTRITION-MIN_NUTRITION)*MouseSim.rand.nextDouble();
	}

	private static boolean canCreate() {
		return food_in_existance < MAX_FOOD;
	}

	private static Food create() {
		if(canCreate()) {
			food_in_existance++;
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

	public void eat() {
		food_in_existance--;
	}

}