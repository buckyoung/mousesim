import java.util.Random;

public class MouseSim {
	
	//* Private Constants
	private static final int MAX_RUNTIME = 5000; // set to -1 to disable
	private static final int GAMESPEED = 350;
	private static final int WORLDSIZEROW = 25;
	private static final int WORLDSIZECOL = 100;

	//* Public Variables
	public static final boolean DEBUG = true;
	public static Random rand = new Random(Double.doubleToLongBits(Math.random()));

	//* Private Fields
	private static String endedReason;
	private static boolean isRunning;
	private static int runtime;
	private static World world;

	//* Public Methods
	public static void endGame(String reason) {
		endedReason = reason;
		Stream.close();
		isRunning = false;
	}

	public static double getRandomDouble(double min, double max) {
		return min + (max - min) * rand.nextDouble();
	}

	public static double getRandomInt(int min, int max) {
		return min + (max - min) * rand.nextInt();
	}

	public static int getRuntime() {
		return runtime;
	}

	public static World getWorld() {
		return world;
	}

	public static int getWorldSizeRow() {
		return WORLDSIZEROW;
	}
	
	public static int getWorldSizeCol() {
		return WORLDSIZECOL;
	}

	//* Main 
	public static void main(String[] args) {
		isRunning = true;
		runtime = 0;
		world = new World(WORLDSIZEROW, WORLDSIZECOL);
		
		Colony.generateSeedMice(10);

		gameLoop();
	}

	//* Private Methods
	private final static void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	private static void gameLoop() {
		while(isRunning) {
			update();
			render(); //increments runtime
			try{
				Thread.sleep(50000/GAMESPEED);
			} catch (InterruptedException e) {
				return;
			}
			//DEBUG
			if(MAX_RUNTIME>0 && runtime==MAX_RUNTIME) {
				isRunning = false;
				endedReason = "the maximum runtime was reached.";
			}
			//ENDDEBUG
		}
		System.out.println();
		System.out.println("The game has ended because... " + endedReason);
	}

	private static void render() {
		clearConsole();
		System.out.println("Runtime: " + runtime++); //debug?
		world.render();
		Stream.print();
	}

	private static void update() {
		Colony.update();
		Food.foodFactory();
	}

}
