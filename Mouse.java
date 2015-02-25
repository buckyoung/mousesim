import java.util.HashMap;
import java.util.Map.Entry;

public class Mouse {
	private static final double AGERATE = 0.01;
	private static final double RESTRATE = 10.25;

	private static final double MIN_LIFESPAN = 3.5;
	private static final double MAX_LIFESPAN = 8.9;
	private final double LIFESPAN;
	
	private static final double MIN_HUNGERRATE = 0.09;
	private static final double MAX_HUNGERRATE = 0.89;
	private static final double MAX_HUNGER = 150.0; // Max hunger need before death
	private final double HUNGERRATE;

	private static final double MIN_FATIGUERATE = 0.01;
	private static final double MAX_FATIGUERATE = 0.54;
	private static final double MAX_FATIGUE = 100.0; // Max fatigue need before death
	private final double FATIGUERATE;
	
	private double hunger, fatigue;
	//thirst, discomfort, energy, sex, warmth, etc etc TODO "I have a lot of...______"

	private double age; // how long it has been living
	//health, sex(M/F), fit to breed, ispregnant, etc etc TODO

	private HashMap<String, Boolean> statusAffects;

	private Position position;
	private final String name;
	private final int birthday;
	private boolean isAlive;

	public Mouse(String name, Position p) {

		this.name = name;
		this.position = p;

		this.hunger = 0.0;
		this.fatigue = 0.0;
		this.age = 0.0;
		this.birthday = MouseSim.getRuntime();

		statusAffects = new HashMap<>(); //weak, diseased, lactose intolerant, ispregnant? etc etc TODO

		// Create life-rates
		LIFESPAN = MIN_LIFESPAN + (MAX_LIFESPAN - MIN_LIFESPAN) * MouseSim.rand.nextDouble();
		HUNGERRATE = MIN_HUNGERRATE + (MAX_HUNGERRATE - MIN_HUNGERRATE) * MouseSim.rand.nextDouble();
		FATIGUERATE = MIN_FATIGUERATE + (MAX_FATIGUERATE - MIN_FATIGUERATE) * MouseSim.rand.nextDouble();
		
		this.isAlive = true;
		Stream.update(name + " was born!");
		MouseSim.getWorld().getWorldNode(this.position).add((Mouse)this);
	}

	private void adjustHunger(double amt) {
		hunger += amt;

		if(hunger < 0.0) {
			hunger = 0.0;
		}

		if(hunger > MAX_HUNGER) {
			this.die("starved to death");
		}
	}

	private void adjustFatigue(double amt) {
		fatigue += amt;

		if(fatigue < 0.0) {
			fatigue = 0.0;
		}

		if(fatigue > MAX_FATIGUE) {
			this.die("died of exhaustion");
		}
	}

	private boolean canEat(double amt) { //REDO
		return hunger > amt;
	}

	private boolean canMove(Direction d, int steps) {
		int size = MouseSim.getWorldSize();

		switch(d) {
			case UP:
				return this.position.row - steps >= 0 ;
			case DOWN:
				return this.position.row + steps <= size-1;
			case LEFT:
				return this.position.col - steps >= 0;
			case RIGHT:
				return this.position.col + steps <= size-1;
			case UPLEFT:
				return this.position.row - steps >= 0 && this.position.col - steps >= 0;
			case UPRIGHT:
				return this.position.row - steps >= 0 && this.position.col + steps <= size-1;
			case DOWNLEFT:
				return this.position.row + steps <= size-1 && this.position.col - steps >= 0;
			case DOWNRIGHT:
				return this.position.row + steps <= size-1 && this.position.col + steps <= size-1;
			default:
				return false;
		}
	}

	private void die(String reason) { //REDO
		String message = name + " has " + reason + "! RIP (" + birthday + "-" + MouseSim.getRuntime() + ")";
		Stream.update(message);

		this.isAlive = false;
		
		//Remove from World if not reincarnated
		if(!this.reincarnation()) MouseSim.getWorld().getWorldNode(this.position).remove(this);
	}

	public boolean isAlive() {
		return isAlive;
	}

	private void move(Direction d, int steps) {

		MouseSim.getWorld().getWorldNode(this.position).remove(this);

		switch (d){
			case UP:
				position.row -= steps;
			break;
			case DOWN:
				position.row += steps;
			break;
			case LEFT:
				position.col -= steps;
			break;
			case RIGHT:
				position.col += steps;
			break;
			case UPLEFT:
				position.row -= steps;
				position.col -= steps;
			break;
			case UPRIGHT:
				position.row -= steps;
				position.col += steps;
			break;
			case DOWNLEFT:
				position.row += steps;
				position.col -= steps;
			break;
			case DOWNRIGHT:
				position.row += steps;
				position.col += steps;
			break;
			default:
		}

		MouseSim.getWorld().getWorldNode(this.position).add(this);

		updateFatigue();
	}

	private void moveRandom(int steps) {

		switch(MouseSim.rand.nextInt(9)){
			case 0:
				if(canMove(Direction.UP, steps)) {
					move(Direction.UP, steps);
				}
			break;
			case 1:
				if(canMove(Direction.DOWN, steps)) {
					move(Direction.DOWN, steps);
				}
			break;
			case 2:
				if(canMove(Direction.LEFT, steps)) {
					move(Direction.LEFT, steps);
				}
			break;
			case 3:
				if(canMove(Direction.RIGHT, steps)) {
					move(Direction.RIGHT, steps);
				}
			break;
			case 4:
				if(canMove(Direction.UPLEFT, steps)) {
					move(Direction.UPLEFT, steps);
				}
			break;
			case 5:
				if(canMove(Direction.UPRIGHT, steps)) {
					move(Direction.UPRIGHT, steps);
				}
			break;
			case 6:
				if(canMove(Direction.DOWNLEFT, steps)) {
					move(Direction.DOWNLEFT, steps);
				}
			break;
			case 7:
				if(canMove(Direction.DOWNRIGHT, steps)) {
					move(Direction.DOWNRIGHT, steps);
				}
			break;
			default:
				adjustFatigue(-RESTRATE);
		}

	}

	private void printStats() {

		System.out.println("Name:   \t" + name);
		System.out.println("Hunger: \t" + hunger);
		System.out.println("Fatigue:\t" + fatigue);
		System.out.println("Age:    \t" + age);
		System.out.println(LIFESPAN + "  " + HUNGERRATE + "  " + FATIGUERATE); //debug

		for(Entry<String, Boolean> entry : statusAffects.entrySet()) {
			String affect = entry.getKey();
			Boolean hasAffect = entry.getValue();

			if(hasAffect) {
				System.out.println("You are " + affect);
			}
		}

		System.out.println();
	}

	private boolean reincarnation() { //REDO
		if(MouseSim.rand.nextInt(5) == 0) {
			this.isAlive = true;
			this.hunger = this.hunger / 2;
			this.fatigue = this.fatigue / 3;
			this.age = 0.0;
			Stream.update(name + " was reincarnated! Amazing!");
			return true;
		}

		return false;
	}

	public void update() {
		if(!isAlive) return;
		
		moveRandom(1); // Mouse can move 1 step per cyle
		updateAge();
		updateHunger();
		//updateFatigue is called in move

		printStats();
	}

	private void updateAge() {
		age += AGERATE;

		if(age > LIFESPAN) {
			this.die("died of old age");
		}
	}

	private void updateHunger() {
		adjustHunger(HUNGERRATE);		
	}

	private void updateFatigue() {
		adjustFatigue(FATIGUERATE);		
	}

}