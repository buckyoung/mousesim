import java.util.Random;
import java.util.HashMap;
import java.util.Map.Entry;

public class Mouse {
	private final double AGERATE = 0.01;
	private final double RESTRATE = 0.25;

	private final double MINLIFESPAN = 2.5;
	private final double MAXLIFESPAN = 3.9;
	private final double LIFESPAN;
	
	private final double MINHUNGERRATE = 0.09;
	private final double MAXHUNGERRATE = 0.89;
	private final double HUNGERRATE;

	private final double MINFATIGUERATE = 0.01;
	private final double MAXFATIGUERATE = 0.54;
	private final double FATIGUERATE;
	
	private double hunger, fatigue;
	//drink, comfort, energy, sex, warmth, etc etc

	private double age; // how long it has been living
	//health, sex(M/F), fit to breed, ispregnant, etc etc

	private HashMap<String, Boolean> statusAffects;

	public Position position;

	private Random rand;

	public final String name;

	public Mouse(String n, Position p) {
		rand = new Random(Double.doubleToLongBits(Math.random()));

		position = p;
		name = n;
		hunger = 0.0;
		fatigue = 0.0;
		age = 0.0;

		statusAffects = new HashMap<>(); //weak, diseased, lactose intolerant, ispregnant? etc etc

		// Create life-rates
		//rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
		LIFESPAN = MINLIFESPAN + (MAXLIFESPAN - MINLIFESPAN) * rand.nextDouble();
		HUNGERRATE = MINHUNGERRATE + (MAXHUNGERRATE - MINHUNGERRATE) * rand.nextDouble();
		FATIGUERATE = MINFATIGUERATE + (MAXFATIGUERATE - MINFATIGUERATE) * rand.nextDouble();
		
	}

	public void update() {
		updateNeeds();
		updateAge();
	}

	private void updateNeeds() {
		switch(rand.nextInt(2)){
			case 0:
				hunger += HUNGERRATE;
				if(hunger > 150.0) {
					MouseSim.gameOver(name + " starved to death.");
				}
			break;
			case 1:
				tire(FATIGUERATE);
				if(fatigue > 100.0) {
					MouseSim.gameOver(name + " was exhausted.");
				}
			break;
		}
	}

	private void updateAge() {
		age += AGERATE;

		System.out.println(LIFESPAN);

		if(age > LIFESPAN){
			MouseSim.gameOver(name + " has died of old age.");
		}
	}

	public void printStats() {
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

	public boolean canEat(double amt) {
		return hunger > amt;
	}

	public void eat(double amt) {
		hunger -= amt;

		if(hunger < 0.0) {
			hunger = 0.0;
		}
	}

	public void rest(double amt) {
		fatigue -= amt;

		if(fatigue < 0.0) {
			fatigue = 0.0;
		}
	}
	public void tire(double amt) {
		fatigue += amt;
	}

	public void moveRandom(World world) {

		switch(rand.nextInt(5)){
			case 0:
				if(world.canMove(Direction.UP, position)) {
					move(Direction.UP);
				}
			break;
			case 1:
				if(world.canMove(Direction.DOWN, position)) {
					move(Direction.DOWN);
				}
			break;
			case 2:
				if(world.canMove(Direction.LEFT, position)) {
					move(Direction.LEFT);
				}
			break;
			case 3:
				if(world.canMove(Direction.RIGHT, position)) {
					move(Direction.RIGHT);
				}
			break;
			default:
				rest(RESTRATE);
		}

	}

	private void move(Direction d) {
		tire(0.1);

		switch (d){
			case UP:
				position.row--;
			break;
			case DOWN:
				position.row++;
			break;
			case LEFT:
				position.col--;
			break;
			case RIGHT:
				position.col++;
			break;
			default:
		}	
	}

}