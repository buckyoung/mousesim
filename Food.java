public class Food {

	//* Private Constants
	private static final int MAX_FOOD = 12;
	private static final double MAX_NUTRITION = 34.532;
	private static final double MIN_NUTRITION = 4.23;
	
	//* Public Fields
	public double CALORIES;

	//* Private Fields
	private WorldNode container;
	private static int food_in_existence = 0;

	//* Public Methods
	public static void foodFactory() {
		if(canGenerate()) {
			Food food = generateFood();
			food.container = MouseSim.getWorld().getRandomWorldNode();
			food.container.add(food);
		}
	}
	
	// Input: hunger // Returns: amt of calories eaten
	public double eat(double hunger) {
		double calories = this.CALORIES;

		this.CALORIES -= hunger;

		if(this.CALORIES <= 0) {
			food_in_existence--;
			container.remove(this);
		}

		return (calories <= hunger) ? calories : hunger;
	}

	//* Private Methods
	private Food() {
		CALORIES = MIN_NUTRITION+(MAX_NUTRITION-MIN_NUTRITION)*MouseSim.rand.nextDouble();
	}

	private static boolean canGenerate() {
		return food_in_existence < MAX_FOOD;
	}

	private static Food generateFood() {
		food_in_existence++;
		return new Food();
	}

}