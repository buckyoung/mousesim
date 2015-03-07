import java.util.LinkedList;

public class Colony {

	//* Private Constants
	private static final int MAX_MICE = MouseSim.getMaxMice();

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
		Stream.history("Colony.generateMice("+number+")");

		if(p == null) {
			p = new Position(MouseSim.rand.nextInt(MouseSim.getWorldSizeRow()), MouseSim.rand.nextInt(MouseSim.getWorldSizeCol()));
		}

		while(number > 0) {
			generateBaby(p, f, m);
			number--;
		}
	}

	public static void update() {
		Statistics.colonyReset();

		for(Mouse mouse : mice) {
			mouse.update();
			if(!mouse.isAlive()) deadMice.add(mouse);

			Statistics.colonyInclude(mouse);
		}

		Statistics.colonyReady();

		while(!deadMice.isEmpty()) {
			Mouse deadMouse = deadMice.remove();

			mice.remove(deadMouse);
			Stream.history("Colony Size: " + mice.size());
			MouseSim.getWorld().getWorldNode(deadMouse.getPosition()).remove(deadMouse);

			if(mice.size() == 1) {
				Stream.update(mice.get(0) + " is the last mouse alive! x_x");
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

	public static int getSize() {
		return mice.size();
	}

	public static Mouse getCandidate(Mouse notThisMouse) {
		for(Mouse mouse : mice) {
			if(mouse.getGender() == Gender.MALE && !mouse.isBaby() && mouse.isAlive() && (mouse.getAge()/mouse.getDNA().getLifespan() < 0.5) && mouse != notThisMouse) return mouse;
		}
		return null;
	}

}