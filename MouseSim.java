public class MouseSim {

	private static final int GAMESPEED = 200;
	public static final int WORLDSIZE = 20;
	private static boolean isRunning;
	private static int runtime;
	private static Mouse mouse1;
	private static Mouse mouse2;
	private static World world;
	private static String endedReason;

	public static void update(Mouse mouse) {
		world.resetCell(mouse.position);
		mouse.moveRandom(world);
		mouse.update();
		Food.createRandom(world);
		world.updateMousePosition(mouse);
	}

	public static void render() {
		clearConsole();
		System.out.println("Runtime: " + runtime++); //debug
		world.render();
	}

	// **** MAIN ****
	public static void main(String[] args) {
		runtime = 0;
		mouse1 = new Mouse("Harvey Wallsqueaker", new Position(0, 0));
		mouse2 = new Mouse("Norman Squeakswell", new Position(WORLDSIZE-1, WORLDSIZE-1));

		world = new World(WORLDSIZE);

		isRunning = true;
		gameLoop();
	}

	public static void gameOver(String reason) {
		endedReason = reason;
		isRunning = false;
	}

	public static void gameLoop() {
		while(isRunning) {
			update(mouse1);
			update(mouse2);
			render();

			mouse1.printStats();
			mouse2.printStats();
			
			try{
				Thread.sleep(50000/GAMESPEED); //the timing mechanism
			} catch (InterruptedException e) {
				return;
			}
		}

		System.out.println();
		System.out.println("The game has ended because... " + endedReason);
	}

	public final static void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
