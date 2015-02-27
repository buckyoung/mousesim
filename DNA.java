public class DNA {

	//* Private Constants
		//physical
	private static final double MAX_AROUSAL_RATE = 2.33;
	private static final double MIN_AROUSAL_RATE = 0.85;
	private static final double MAX_FATIGUE_RATE = 0.54;
	private static final double MIN_FATIGUE_RATE = 0.01;
	private static final double MAX_HUNGER_RATE = 0.44;
	private static final double MIN_HUNGER_RATE = 0.045;
	private static final double MAX_LIFESPAN = 13.9;
	private static final double MIN_LIFESPAN = 7.5;
	private static final double MAX_PUBERTY_AGE = 1.5;
	private static final double MIN_PUBERTY_AGE = 0.9;
	private static final double MAX_REST_RATE = 1.95;
	private static final double MIN_REST_RATE = 0.55;
	private static final double MAX_SEXUAL_POTENCY = 100.0;
	private static final double MIN_SEXUAL_POTENCY = 1.0;
		//mental
	private static final double MAX_AROUSAL_LIMIT = 90.0;
	private static final double MIN_AROUSAL_LIMIT = 50.0;
	private static final double MAX_FATIGUE_LIMIT = 80.0;
	private static final double MIN_FATIGUE_LIMIT = 40.0;
	private static final double MAX_HUNGER_LIMIT = 70.0;
	private static final double MIN_HUNGER_LIMIT = 30.0;

	//* Physical Attributes
	private final double AROUSAL_RATE;
	private final double FATIGUE_RATE;
	private final double HUNGER_RATE;
	private final double LIFESPAN;
	private final double PUBERTY_AGE;
	private final double REST_RATE;
	private final double SEXUAL_POTENCY;

	//* Mental Attributes
	private final double AROUSAL_LIMIT;
	private final double FATIGUE_LIMIT;
	private final double HUNGER_LIMIT;

	//* Last Name
	private final String lastName;

	public DNA() {
		//physical
		this.AROUSAL_RATE = MouseSim.getRandomDouble(MIN_AROUSAL_RATE, MAX_AROUSAL_RATE);
		this.FATIGUE_RATE = MouseSim.getRandomDouble(MIN_FATIGUE_RATE, MAX_FATIGUE_RATE);
		this.HUNGER_RATE = MouseSim.getRandomDouble(MIN_HUNGER_RATE, MAX_HUNGER_RATE);
		this.LIFESPAN = MouseSim.getRandomDouble(MIN_LIFESPAN, MAX_LIFESPAN);
		this.PUBERTY_AGE = MouseSim.getRandomDouble(MIN_PUBERTY_AGE, MAX_PUBERTY_AGE);
		this.REST_RATE = MouseSim.getRandomDouble(MIN_REST_RATE, MAX_REST_RATE);
		this.SEXUAL_POTENCY = MouseSim.getRandomDouble(MIN_SEXUAL_POTENCY, MAX_SEXUAL_POTENCY);
		//mental
		this.AROUSAL_LIMIT = MouseSim.getRandomDouble(MIN_AROUSAL_LIMIT, MAX_AROUSAL_LIMIT);
		this.FATIGUE_LIMIT = MouseSim.getRandomDouble(MIN_FATIGUE_LIMIT, MAX_FATIGUE_LIMIT);
		this.HUNGER_LIMIT = MouseSim.getRandomDouble(MIN_HUNGER_LIMIT, MAX_HUNGER_LIMIT);
		//lastname
		this.lastName = NameGenerator.generateLastName();
	}

	public DNA(DNA father, DNA mother) {
		//physical
		this.AROUSAL_RATE = determineAttribute(father.getArousalRate(), mother.getArousalRate());
		this.FATIGUE_RATE = determineAttribute(father.getFatigueRate(), mother.getFatigueRate());
		this.HUNGER_RATE = determineAttribute(father.getHungerRate(), mother.getHungerRate());
		this.LIFESPAN = determineAttribute(father.getLifespan(), mother.getLifespan());
		this.PUBERTY_AGE = determineAttribute(father.getPubertyAge(), mother.getPubertyAge());
		this.REST_RATE = determineAttribute(father.getRestRate(), mother.getRestRate());
		this.SEXUAL_POTENCY = determineAttribute(father.getSexualPotency(), mother.getSexualPotency());
		//mental
		this.AROUSAL_LIMIT = determineAttribute(father.getArousalLimit(), mother.getArousalLimit());
		this.FATIGUE_LIMIT = determineAttribute(father.getFatigueLimit(), mother.getFatigueLimit());
		this.HUNGER_LIMIT = determineAttribute(father.getHungerLimit(), mother.getHungerLimit());
		//lastname
		this.lastName = father.getLastName();
	}

	private static double determineAttribute(double father, double mother) {
		int choice = MouseSim.rand.nextInt(3);
		if(choice == 0) return father;
		if(choice == 1) return mother;
		return (father + mother)/2; //average
	}

	private static int determineAttribute(int father, int mother) {
		int choice = MouseSim.rand.nextInt(3);
		if(choice == 0) return father;
		if(choice == 1) return mother;
		return (father + mother)/2; //average
	}

	//physical
	public double getArousalRate() {
		return this.AROUSAL_RATE;
	}
	public double getFatigueRate() {
		return this.FATIGUE_RATE;
	}
	public double getHungerRate() {
		return this.HUNGER_RATE;
	}
	public double getLifespan() {
		return this.LIFESPAN;
	}
	public double getPubertyAge() {
		return this.PUBERTY_AGE;
	}
	public double getRestRate() {
		return this.REST_RATE;
	}
	public double getSexualPotency() {
		return this.SEXUAL_POTENCY;
	}

	//mental
	public double getArousalLimit() {
		return this.AROUSAL_LIMIT;
	}
	public double getFatigueLimit() {
		return this.FATIGUE_LIMIT;
	}
	public double getHungerLimit() {
		return this.HUNGER_LIMIT;
	}

	//lastname
	public String getLastName() {
		return this.lastName;
	}

	@Override
	public String toString() {
		return 
		"\n*** Physical Attributes ***" + 
		"\n\tAROUSAL_RATE:   " + this.AROUSAL_RATE +
		"\n\tFATIGUE_RATE:   " + this.FATIGUE_RATE +
		"\n\tHUNGER_RATE:    " + this.HUNGER_RATE +
		"\n\tLIFESPAN:       " + this.LIFESPAN +
		"\n\tPUBERTY_AGE:    " + this.PUBERTY_AGE +
		"\n\tREST_RATE:      " + this.REST_RATE +
		"\n\tSEXUAL_POTENCY: " + this.SEXUAL_POTENCY +
		"\n*** Mental Attributes ***" + 
		"\n\tAROUSAL_LIMIT:  " + this.AROUSAL_LIMIT +
		"\n\tFATIGUE_LIMIT:  " + this.FATIGUE_LIMIT +
		"\n\tHUNGER_LIMIT:   " + this.HUNGER_LIMIT;
	}
}