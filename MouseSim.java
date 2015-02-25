import java.util.ArrayList;
import java.util.Random;

public class MouseSim {
	public static final boolean DEBUG = true;
	public static Random rand = new Random(Double.doubleToLongBits(Math.random()));

	private static final int GAMESPEED = 200;
	private static final int WORLDSIZE = 30;
	private static int runtime;
	private static boolean isRunning;
	private static ArrayList<Mouse> mice;
	private static World world;
	private static String endedReason;

	// **** MAIN ****
	public static void main(String[] args) {
		runtime = 0;
		world = new World(WORLDSIZE);
		
		mice = new ArrayList<>();

		//REDO manual mice add's
		mice.add(new Mouse("Harvey Wallsqueaker", new Position(0, 0)));
		mice.add(new Mouse("Norman Squeakswell", new Position(WORLDSIZE-1, WORLDSIZE-1)));
		mice.add(new Mouse("Ray Charles", new Position(WORLDSIZE/2, WORLDSIZE/2)));
		mice.add(new Mouse("Splinter", new Position(5, 5)));
		mice.add(new Mouse("Sam Mouser", new Position(10, 5)));		

		isRunning = true;
		gameLoop();
	}

	private final static void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	private static void endGame(String reason) {
		endedReason = reason;
		Stream.close();
		isRunning = false;
	}

	private static void gameLoop() {
		Mouse deadMouse; //REDO deadmouse
		while(isRunning) {
			deadMouse = null; //REDO deadmouse

			for(Mouse m : mice) {
				m.update();
				if(!m.isAlive()) deadMouse = m; //REDO deadmouse
			}

			if(deadMouse != null) { //REDO deadmouse
				mice.remove(deadMouse); //REDO deadmouse
				if(mice.size() == 1) {
					Stream.update(mice.get(0).getName() + " is the last mouse alive! x_x");
				}
			} //REDO deadmouse

			if(mice.isEmpty()) {
				endGame("all the mice have died.");
			}

			Food.createRandom(); //REDO

			render();
			Stream.print();
			
			try{
				Thread.sleep(50000/GAMESPEED);
			} catch (InterruptedException e) {
				return;
			}
		}

		System.out.println();
		System.out.println("The game has ended because... " + endedReason);
	}

	public static int getRuntime() {
		return runtime;
	}

	public static World getWorld() {
		return world;
	}

	public static int getWorldSize() {
		return WORLDSIZE;
	}

	private static void render() {
		clearConsole();
		System.out.println("Runtime: " + runtime++); //debug
		world.render();
	}

}
