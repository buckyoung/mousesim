import java.util.HashMap;
import java.util.Map.Entry;

public class Mouse {
	//* Private Constants
		// Game Attributes
	private static final double NEED_DEATH = 100.0;
	private static final double AGE_RATE = 0.01;
	private static final int GESTATION_TIME = 150;
	private static final int SEXUAL_POTENCY_CUTOFF = 10;
		// Mouse Attributes
	// private static final int SKIP_EAT = 7; //compute with logs now
	private static final int SKIP_REST = 35; //redo

	//* Private Fields
		// Game Attributes
	private int skipCycles;
		// Mouse Attributes
	private double arousal, hunger, fatigue;
	//thirst, discomfort, energy, sex, warmth, etc etc TODO "I have a lot of...______"
	private double age; // how long it has been living
	//health, sex(M/F), fit to breed, etc etc TODO
	
	private final int birthday;
	private Gender gender;
	private boolean isAlive;
	private final Mouse father;
	private final String firstName;
	private final Mouse mother; 
	private Position position;
	private boolean isPregnant;
	private boolean isPresident;
	private int walkRate;

	private AI brain;
	private DNA dna;

	/*  
	 * * * Public Methods * * *
	 */
	public Mouse(Position pos, Mouse father, Mouse mother) {

		this.birthday = MouseSim.getRuntime();
		this.gender = (MouseSim.rand.nextInt(2) == 1) ? Gender.MALE : Gender.FEMALE;
		this.isAlive = true;
		this.father = father;
		this.firstName = NameGenerator.generateFirstName(this.gender); 
		this.mother = mother;
		this.position = pos;
		this.isPregnant = false;
		this.isPresident = false;
		this.walkRate = 1;

		skipCycles = 0;

		this.arousal = 0.0;
		this.hunger = 0.0;
		this.fatigue = 0.0;
		this.age = 0.0;

		this.brain = new AI(this);
		
		if(mother != null && father != null){
			this.dna = new DNA(father.getDNA(), mother.getDNA());
		} else {
			this.dna = new DNA();
		}
		
		//Stream.update(this.firstName + " " + this.dna.getLastName() + " (" + this.gender + ") was born!");
		MouseSim.getWorld().getWorldNode(this.position).add((Mouse)this);
	}

	public void giveBirth(Mouse father) {
		if(!isAlive) return;
		//Stream.debug(this + " is pooping babies!");
		Colony.generateMice(MouseSim.getRandomInt(1,3), this.position, father, this);
		this.isPregnant = false;
	}

	public double getAge() {
		return this.age;
	}

	public double getArousal() {
		return this.arousal;
	}

	public DNA getDNA() {
		return this.dna;
	}

	public String getFatherName() {
		return (this.father == null ? "(No Record)" : this.father.getName());
	}

	public Gender getGender() {
		return this.gender;
	}

	public String getMotherName() {
		return (this.mother == null ? "(No Record)" : this.mother.getName());
	}

	public String getName() {
		return (this.isAlive?"":"The Late ")+(this.isPresident?"President ":"")+this.firstName + " " + this.dna.getLastName();
	}

	public String getRepresention() {
		String result = "" + this.dna.getLastName().charAt(0);
		return result;
	}

	public Position getPosition() {
		return this.position;
	}

	public boolean isAlive() {
		return this.isAlive;
	}

	public boolean isBaby() {
		return this.age < this.dna.getPubertyAge();
	}

	public boolean isPregnant() {
		return this.isPregnant;
	}

	public void update() {
		if(!isAlive) return;
		
		updateAge(); 
		//printStats();

		if(skipCycles > 0) {
			skipCycles--;
			return;
		}

		switch(brain.decideAction()) {
			case MOVE:
				actionMove();
			break;

			case EAT:
				actionEat();
			break;

			case SEX:
				actionSex();
			break;

			case REST: // redo: make a smart decision about how long to rest? //wake up if another need gets critical?
				actionRest();
			break;

			default:
		}

	}

	private void actionMove() {
		move(brain.decideDirection(this.walkRate), this.walkRate);
		adjustArousal(this.dna.getArousalRate());
		adjustFatigue(this.dna.getFatigueRate());
		adjustHunger(this.dna.getHungerRate());
	}

	private void actionEat() {
		skipCycles = (int)(Math.log( this.eat(MouseSim.getWorld().getWorldNode(this.position).getAnyFood()) ) * 2);
		adjustArousal(this.dna.getArousalRate() * skipCycles);
		Stream.history(this + " decided to eat for "+ skipCycles +" cycles");
	}

	private void actionSex() {
		Stream.history(this + " is feeling frisky...");

		Mouse unpregantFemalePartner = MouseSim.getWorld().getWorldNode(this.position).getUnpregnantFemale();

		if(unpregantFemalePartner == null || unpregantFemalePartner.getArousal() < unpregantFemalePartner.getDNA().getArousalLimit())
		return; //she doesnt exist or want to bone

		unpregantFemalePartner.resetArousal();
		this.resetArousal();
		
		if(MouseSim.rand.nextInt((int)this.dna.getSexualPotency()) > SEXUAL_POTENCY_CUTOFF) {
			Scheduler.addEvent(new Event(GESTATION_TIME, EventFunction.GIVE_BIRTH, this, unpregantFemalePartner));
			unpregantFemalePartner.conceive();
			
			if(isPresident){
				Stream.update(this +" planted a seed in "+unpregantFemalePartner);
			}
		}
	}

	private void actionRest() {
		Stream.history(this + " decided to take a little snooze... zZzz...");
		adjustArousal(this.dna.getArousalRate() * SKIP_REST);
		adjustFatigue(-this.dna.getRestRate() * SKIP_REST);
		adjustHunger(this.dna.getHungerRate() * (SKIP_REST/10));
		skipCycles = SKIP_REST;
	}

	public void resetArousal() {
		this.adjustArousal(-this.arousal);
	}

	public void conceive() {
		this.isPregnant = true;
		//Stream.debug(this.getName() + " is expecting!!!!!!");
	}

	public void makePresident() {
		if(!isAlive) return;
		Stream.update(this + " has been elected president!");
		this.isPresident = true;
		Stream.update("All hail " + this + "!");
	}

	/*
	 * * * Private Methods * * * 
	 */
	private void adjustArousal(double amt) {
		if(!isAlive || isBaby()) return;

		arousal += amt;

		if(arousal < 0.0) {
			arousal = 0.0;
		}
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
		String message = this + " has " + reason + "! RIP (" + birthday + "-" + MouseSim.getRuntime() + ")";
		
		if(isPresident) {
			Stream.update(message);
		} else {
			Stream.history(message);
		}

		//if(this.reincarnation()) return; //Let die if not reincarnated

		this.isAlive = false;

		Stream.history("Father: " + this.getFatherName() + "  |  Mother: " + this.getMotherName() + " " + this.dna);

		Statistics.incrementDead();
	}

	private double eat(Food food) {
		if(!isAlive) return 0.0;

		double amt = food.eat(hunger);
		adjustHunger(-amt);

		return amt;
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

		System.out.println("Name:   \t" + this);
		System.out.println("Hunger: \t" + hunger);
		System.out.println("Fatigue:\t" + fatigue);
		System.out.println("Age:    \t" + age);
		System.out.println(this.dna.getLifespan() + "  " + this.dna.getHungerRate() + "  " + this.dna.getFatigueRate()); //debug

		System.out.println();
	}

	private boolean reincarnation() { //REDO
		if(MouseSim.rand.nextInt(5) == 0) {
			this.hunger = this.hunger / 2;
			this.fatigue = this.fatigue / 3;
			this.age = 0.0;
			Stream.update(this + " was reincarnated! Amazing!");
			return true;
		}

		return false;
	}

	private void updateAge() {
		if(!isAlive) return;

		age += AGE_RATE;

		if(this.age > this.dna.getLifespan()) {
			this.die("died of old age");
		}
	}

	/// redo: MENTAL ATTRIBUTES = BRAIN, PHYSICAL ATTRIBUTES = BODY, BOTH PASSED TO CHILD
	//* Private Inner Class
	private class AI { 
		//* Private Constants
		private static final int CHANGE_DIRECTION_RATE = 12; // 1 in ...

		private Mouse body;
		private Direction lastDirection;

		private AI(Mouse body) {
			this.body = body;
			lastDirection = null;
		}

		private MouseAction decideAction() {
			WorldNode currentLocation = MouseSim.getWorld().getWorldNode(body.position);

			if(currentLocation.hasFood() && hunger > body.dna.getHungerLimit()) {
				return MouseAction.EAT;
			}

			if(body.getGender() == Gender.MALE && !body.isBaby() && arousal > body.dna.getArousalLimit() && currentLocation.hasPotentialPartner(body.gender)) {
				return MouseAction.SEX;
			}

			if(fatigue > body.dna.getFatigueLimit()) {
				return MouseAction.REST;
			}

			return MouseAction.MOVE;
		}

		private Direction decideDirection(int steps) {
			if(!isAlive) return null;

			if(lastDirection != null && MouseSim.rand.nextInt(CHANGE_DIRECTION_RATE) != 0 && canMove(lastDirection, steps)) {
				return lastDirection;
			}

			return moveRandom(steps);
		}

		private Direction moveRandom(int steps) {
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

			int sizeRow = MouseSim.getWorldSizeRow();
			int sizeCol = MouseSim.getWorldSizeCol();

			switch(d) {
				case UP:
					return body.position.row - steps >= 0 ;
				case DOWN:
					return body.position.row + steps <= sizeRow-1;
				case LEFT:
					return body.position.col - steps >= 0;
				case RIGHT:
					return body.position.col + steps <= sizeCol-1;
				case UPLEFT:
					return body.position.row - steps >= 0 && body.position.col - steps >= 0;
				case UPRIGHT:
					return body.position.row - steps >= 0 && body.position.col + steps <= sizeCol-1;
				case DOWNLEFT:
					return body.position.row + steps <= sizeRow-1 && body.position.col - steps >= 0;
				case DOWNRIGHT:
					return body.position.row + steps <= sizeRow-1 && body.position.col + steps <= sizeCol-1;
				default:
					return false;
			}
		}

	}

	@Override
	public String toString() {
		return this.getName();
	}

}