import java.util.Random;

public class MouseSim {
	
	//* Private Constants
	private static int MAX_MICE = 150;
	private static int INITIAL_MICE = 20;
	private static int MAX_RUNTIME = 12000; // set to -1 to disable
	private static int GAMESPEED = 350;
	private static final int WORLDSIZE_ROW = 25;
	private static final int WORLDSIZE_COL = 50;
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

	public static int getMaxMice() {
		return MAX_MICE;
	}

	public static double getRandomDouble(double min, double max) {
		return min + (max - min) * rand.nextDouble();
	}

	public static int getRandomInt(int min, int max) { //includes both max and min
		return rand.nextInt(max - min + 1) + min;
	}

	public static int getRuntime() {
		return runtime;
	}

	public static World getWorld() {
		return world;
	}

	public static int getWorldSizeRow() {
		return WORLDSIZE_ROW;
	}
	
	public static int getWorldSizeCol() {
		return WORLDSIZE_COL;
	}

	//* Main 
	public static void main(String[] args) {
		if(args.length != 0 && args.length != 4) {
			System.out.println(">> java MouseSim numStartingMice maxNumMice maxRuntime gameSpeed");
			return;
		}

		isRunning = true;
		runtime = 0;	

		if(args.length == 6) {
			INITIAL_MICE = Integer.parseInt(args[0]);
			MAX_MICE = Integer.parseInt(args[1]);
			MAX_RUNTIME = Integer.parseInt(args[2]);
			GAMESPEED = Integer.parseInt(args[3]); 
		} 

		world = new World(WORLDSIZE_ROW, WORLDSIZE_COL);
		Colony.generateMice(INITIAL_MICE, null, null, null);

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
			if(MAX_RUNTIME>0 && runtime==MAX_RUNTIME+1) {
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
		
		System.out.println(
			"Runtime: " + runtime++ + (MAX_RUNTIME>0?" / "+MAX_RUNTIME:"") +
			"\t Colony Size: " + Colony.getSize() + " / " + MAX_MICE + 
			" ("+Statistics.getNumberMale()+"M|"+Statistics.getNumberFemale()+"F) ("+
			Statistics.getNumberDead()+"dead)");
		
		System.out.println("Average Age of Colony: "+Statistics.getAverageAge()+" ("+Statistics.getNumberBaby()+"baby) " +"("+Statistics.getNumberPregnant()+"pregnant)");

		System.out.println("Average Lifespan: "+Statistics.getAverageLifespan() +" [initial:"+Statistics.getInitialLifespan()+"]");

		System.out.println("Sitting President: " + (PresidentController.getPresident() == null ? "None":PresidentController.getPresident()+ " [son of "+ PresidentController.getPresident().getFatherName() +"]"));

		world.render();
		Stream.print();
	}

	private static void update() {
		Colony.update();
		Food.foodFactory();
		Scheduler.update();
		PresidentController.update();
		Statistics.update();
	}

}
