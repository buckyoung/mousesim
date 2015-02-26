import java.util.LinkedList;

public class Colony {

	private static final int MAX_MICE = 15;

	private static LinkedList<Mouse> mice = new LinkedList<>();
	private static LinkedList<Mouse> deadMice = new LinkedList<>();

	public static void generateMice(int number) {
		//REDO: generate arbitrary amount with names and stuff
		mice.add(new Mouse("Harvey Wallsqueaker", new Position(0, 0)));
		mice.add(new Mouse("Norman Squeakswell", new Position(MouseSim.getWorldSize()-1, MouseSim.getWorldSize()-1)));
		mice.add(new Mouse("Ray Charles", new Position(MouseSim.getWorldSize()/2, MouseSim.getWorldSize()/2)));
		mice.add(new Mouse("Splinter", new Position(5, 5)));
		mice.add(new Mouse("Sam Mouser", new Position(10, 5)));	
	}

	public static void update() {
		for(Mouse mouse : mice) {
			mouse.update();
			if(!mouse.isAlive()) deadMice.add(mouse);
		}

		while(!deadMice.isEmpty()) {
			Mouse deadMouse = deadMice.remove();

			mice.remove(deadMouse);
			MouseSim.getWorld().getWorldNode(deadMouse.getPosition()).remove(deadMouse);

			if(mice.size() == 1) {
				Stream.update(mice.get(0).getName() + " is the last mouse alive! x_x");
			}
		}

		if(mice.isEmpty()) {
			MouseSim.endGame("all the mice have died.");
		}

	}

}