import java.util.HashMap;
import java.util.Map.Entry;

public class Mouse {
	//* Private Constants
		// Game Attributes
	private static final double NEED_DEATH = 100.0;
		// Mouse Attributes
	private static final double AGE_RATE = 0.01;
	private static final double AROUSAL_RATE = 2.33; //redo: make a physical attribute with mid-hi variability
	private static final double MAX_FATIGUE_RATE = 0.54;
	private static final double MIN_FATIGUE_RATE = 0.01;
	private static final double MAX_HUNGER_RATE = 0.44;
	private static final double MIN_HUNGER_RATE = 0.045;
	private static final double MAX_LIFESPAN = 8.9;
	private static final double MIN_LIFESPAN = 3.5;
	private static final double REST_RATE = 1.55; //redo: make a physical attribute with low variability
	private static final int SKIP_EAT = 7;
	private static final int SKIP_REST = 35;

	//* Private Fields
		// Game Attributes
	private final double LIFESPAN;
	private int skipCycles;
		// Mouse Attributes
	private final double HUNGER_RATE;
	private final double FATIGUE_RATE;
	private double arousal, hunger, fatigue;
	//thirst, discomfort, energy, sex, warmth, etc etc TODO "I have a lot of...______"
	private double age; // how long it has been living
	//health, sex(M/F), fit to breed, ispregnant, etc etc TODO
	
	private final int birthday;
	private Gender gender;
	private boolean isAlive;
	private final Mouse father;
	private final String firstName;
	private final String lastName;
	private final Mouse mother; 
	private Position position;
	private int walkRate;

	private AI brain;

	//* Public Methods
	public Mouse(String fname, String lname, Position pos, Mouse mother, Mouse father) {

		this.birthday = MouseSim.getRuntime();
		this.gender = (MouseSim.rand.nextInt(2) == 1) ? Gender.MALE : Gender.FEMALE;
		this.isAlive = true;
		this.father = father;
		this.firstName = fname; //redo: generate name and remove from param list (pass in father lastname)
		this.lastName = lname; //redo: generate
		this.mother = mother;
		this.position = pos;
		this.walkRate = 1;

		skipCycles = 0;

		this.arousal = 0.0;
		this.hunger = 0.0;
		this.fatigue = 0.0;
		this.age = 0.0;

		this.brain = new AI(this);

		// Create life-rates
		LIFESPAN = MIN_LIFESPAN + (MAX_LIFESPAN - MIN_LIFESPAN) * MouseSim.rand.nextDouble();
		HUNGER_RATE = MIN_HUNGER_RATE + (MAX_HUNGER_RATE - MIN_HUNGER_RATE) * MouseSim.rand.nextDouble();
		FATIGUE_RATE = MIN_FATIGUE_RATE + (MAX_FATIGUE_RATE - MIN_FATIGUE_RATE) * MouseSim.rand.nextDouble();
		
		Stream.update(this.firstName + " " + this.lastName + " (" + this.gender + ") " + " was born!");
		MouseSim.getWorld().getWorldNode(this.position).add((Mouse)this);
	}

	public Gender getGender() {
		return this.gender;
	}

	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	public Position getPosition() {
		return this.position;
	}

	public boolean isAlive() {
		return this.isAlive;
	}

	public void update() {
		if(!isAlive) return;
		
		updateAge(); 
		printStats();

		if(skipCycles != 0) {
			skipCycles--;
			return;
		}

		switch(brain.decideAction()) {
			case MOVE:
				move(brain.decideDirection(this.walkRate), this.walkRate);
				adjustArousal(AROUSAL_RATE);
				adjustFatigue(FATIGUE_RATE);
				adjustHunger(HUNGER_RATE);
			break;

			case EAT:
				this.eat(MouseSim.getWorld().getWorldNode(this.position).getAnyFood());
				adjustArousal(AROUSAL_RATE * SKIP_EAT);
				skipCycles = SKIP_EAT;
				Stream.update(getName() + " decided to eat!");
			break;

			case SEX:
				this.adjustArousal(-10000.0);
				Stream.update(getName() + " is feeling frisky...");
			break;

			case REST: // redo: make a smart decision about how long to rest? //wake up if another need gets critical?
				adjustArousal(AROUSAL_RATE * SKIP_REST);
				adjustFatigue(-REST_RATE * SKIP_REST);
				adjustHunger(HUNGER_RATE * (SKIP_REST/10));
				skipCycles = SKIP_REST;
				Stream.update(getName() + " decided to take a little snooze... zZzz...");
			break;

			default:
		}

	}

	//* Private Methods
	private void adjustArousal(double amt) {
		if(!isAlive) return;

		arousal += amt;

		if(arousal < 0.0) {
			arousal = 0.0;
		}

		// if(arousal > NEED_DEATH) {
		// 	this.die("starved to death");
		// }
	}

	private void adjustHunger(double amt) {
		if(!isAlive) return;

		hunger += amt;

		if(hunger < 0.0) {
			hunger = 0.0;
		}

		if(hunger > NEED_DEATH) {
			this.die("starved to death");
		}
	}

	private void adjustFatigue(double amt) {
		if(!isAlive) return;

		fatigue += amt;

		if(fatigue < 0.0) {
			fatigue = 0.0;
		}

		if(fatigue > NEED_DEATH) {
			this.die("died of exhaustion");
		}
	}

	private void die(String reason) { //REDO
		String message = getName() + " has " + reason + "! RIP (" + birthday + "-" + MouseSim.getRuntime() + ")";
		Stream.update(message);

		//Kill if not reincarnated
		if(!this.reincarnation()) this.isAlive = false;;
	}

	private void eat(Food food) {
		if(!isAlive) return;

		adjustHunger(-food.eat(hunger));
	}

	private void move(Direction d, int steps) {
		if(d == null || !isAlive) return;

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

		brain.lastDirection = d;

		MouseSim.getWorld().getWorldNode(this.position).add(this);
	}

	private void printStats() {
		if(!isAlive) return;

		System.out.println("Name:   \t" + getName());
		System.out.println("Hunger: \t" + hunger);
		System.out.println("Fatigue:\t" + fatigue);
		System.out.println("Age:    \t" + age);
		System.out.println(LIFESPAN + "  " + HUNGER_RATE + "  " + FATIGUE_RATE); //debugp

		System.out.println();
	}

	private boolean reincarnation() { //REDO
		if(MouseSim.rand.nextInt(5) == 0) {
			this.hunger = this.hunger / 2;
			this.fatigue = this.fatigue / 3;
			this.age = 0.0;
			Stream.update(getName() + " was reincarnated! Amazing!");
			return true;
		}

		return false;
	}

	private void updateAge() {
		if(!isAlive) return;

		age += AGE_RATE;

		if(age > LIFESPAN) {
			this.die("died of old age");
		}
	}

	/// redo: MENTAL ATTRIBUTES = BRAIN, PHYSICAL ATTRIBUTES = BODY, BOTH PASSED TO CHILD
	//* Private Inner Class
	private class AI { 
		//* Private Constants
		private static final double HUNGER_LIMIT = 50.0; //Redo: set on a per-mouse basis -- pass to child
		private static final double FATIGUE_LIMIT = 70.0; //Redo: set on a per-mouse basis -- pass to child
		private static final double AROUSAL_LIMIT = 30.0; //Redo: set on a per-mouse basis -- pass to child
		private static final int CHANGE_DIRECTION_RATE = 2; // 1 in ...

		private Mouse body;
		private Direction lastDirection;

		private AI(Mouse body) {
			this.body = body;
			lastDirection = null;
		}

		private MouseAction decideAction() {
			WorldNode currentLocation = MouseSim.getWorld().getWorldNode(body.position);

			if(currentLocation.hasFood() && hunger > HUNGER_LIMIT) {
				return MouseAction.EAT;
			}

			if(body.gender == Gender.MALE && arousal > AROUSAL_LIMIT && currentLocation.hasPotentialPartner(body.gender)) {
				return MouseAction.SEX;
			}

			if(fatigue > FATIGUE_LIMIT) {
				return MouseAction.REST;
			}

			return MouseAction.MOVE;
		}

		private Direction decideDirection(int steps) {
			if(!isAlive) return null;

			if(lastDirection != null && MouseSim.rand.nextInt(CHANGE_DIRECTION_RATE) != 0 && canMove(lastDirection, steps)) {
				return lastDirection;
			}

			switch(MouseSim.rand.nextInt(8)){
				case 0:
					if(canMove(Direction.UP, steps)) {
						return Direction.UP;
					}
				break;
				case 1:
					if(canMove(Direction.DOWN, steps)) {
						return Direction.DOWN;
					}
				break;
				case 2:
					if(canMove(Direction.LEFT, steps)) {
						return Direction.LEFT;
					}
				break;
				case 3:
					if(canMove(Direction.RIGHT, steps)) {
						return Direction.RIGHT;
					}
				break;
				case 4:
					if(canMove(Direction.UPLEFT, steps)) {
						return Direction.UPLEFT;
					}
				break;
				case 5:
					if(canMove(Direction.UPRIGHT, steps)) {
						return Direction.UPRIGHT;
					}
				break;
				case 6:
					if(canMove(Direction.DOWNLEFT, steps)) {
						return Direction.DOWNLEFT;
					}
				break;
				case 7:
					if(canMove(Direction.DOWNRIGHT, steps)) {
						return Direction.DOWNRIGHT;
					}
				break;
				default:

			}
			return null;
		}

		private boolean canMove(Direction d, int steps) {
			if(!isAlive) return false;

			int size = MouseSim.getWorldSize();

			switch(d) {
				case UP:
					return body.position.row - steps >= 0 ;
				case DOWN:
					return body.position.row + steps <= size-1;
				case LEFT:
					return body.position.col - steps >= 0;
				case RIGHT:
					return body.position.col + steps <= size-1;
				case UPLEFT:
					return body.position.row - steps >= 0 && body.position.col - steps >= 0;
				case UPRIGHT:
					return body.position.row - steps >= 0 && body.position.col + steps <= size-1;
				case DOWNLEFT:
					return body.position.row + steps <= size-1 && body.position.col - steps >= 0;
				case DOWNRIGHT:
					return body.position.row + steps <= size-1 && body.position.col + steps <= size-1;
				default:
					return false;
			}
		}

	}

}