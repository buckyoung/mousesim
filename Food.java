import java.util.Random;

public class Food {
	
	private static final int FOODSCARCITY = 10;
	private static final int MAXFOOD = 8;
	private static final double NUTRITION = 32.43;
	private static int currentFood = 0;

	private static Random rand = new Random(Double.doubleToLongBits(Math.random()));


	public static boolean canCreate() {
		return currentFood < MAXFOOD;
	}

	public static void createRandom(World world) {
		if(canCreate() && rand.nextInt(FOODSCARCITY) == 0){
			Position p = new Position();
			p.row = rand.nextInt(MouseSim.WORLDSIZE);
			p.col = rand.nextInt(MouseSim.WORLDSIZE);
			world.addFood(p);
			currentFood++;
		}
	}

	public static void eatenBy(Mouse mouse) {
		currentFood--;

		if(mouse.canEat(NUTRITION)) {
			mouse.eat(NUTRITION);
		}
	}
}