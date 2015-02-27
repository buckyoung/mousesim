import java.util.LinkedList;

public class Colony {

	//* Private Constants
	private static final int MAX_MICE = 15;

	//* Private Fields
	private static LinkedList<Mouse> mice = new LinkedList<>();
	private static LinkedList<Mouse> bornMice = new LinkedList<>();
	private static LinkedList<Mouse> deadMice = new LinkedList<>();

	//* Public Methods
	public static void generateBaby(Position position, Mouse father, Mouse mother) {
		if(canCreateMouse()) {
			bornMice.add(new Mouse(new Position(position), father, mother));
		}
	}

	public static void generateMice(int number, Position p, Mouse f, Mouse m) {
		if(p == null) {
			p = new Position(MouseSim.rand.nextInt(MouseSim.getWorldSizeRow()), MouseSim.rand.nextInt(MouseSim.getWorldSizeCol()));
		}

		while(number > 0) {	
			generateBaby(p, f, m);
			number--;
		}
	}

	public static void update() {
		for(Mouse mouse : mice) {
			mouse.update();
			if(!mouse.isAlive()) deadMice.add(mouse);
		}

		while(!deadMice.isEmpty()) {
			Mouse deadMouse = deadMice.remove();

			mice.remove(deadMouse);
			Stream.history("Colony Size: " + mice.size());
			MouseSim.getWorld().getWorldNode(deadMouse.getPosition()).remove(deadMouse);

			if(mice.size() == 1) {
				Stream.update(mice.get(0).getName() + " is the last mouse alive! x_x");
			}
		}

		while(!bornMice.isEmpty()) {
			Mouse bornMouse = bornMice.remove();
			mice.add(bornMouse);
			Stream.history("Colony Size: " + mice.size());
		}

		if(mice.isEmpty()) {
			MouseSim.endGame("all the mice have died.");
		}
	}

	private static boolean canCreateMouse() {
		return mice.size() < MAX_MICE;
	}

}