public class Statistics {

	//Private constants
	private static final int UPDATE_LIFESPAN = 5000;

	//Private fields
	private static double ageAverage = 0.0;
	private static double ageSummation = 0.0;

	private static double lifespanAverage = 0.0;
	private static double lifespanSummation = 0.0;
	private static double initialLifespan = 0.0;

	private static int numberMale = 0;
	private static int numberFemale = 0;
	private static int numberBaby = 0;
	private static int numberPregnant = 0;
	private static int numberDead = 0;

	private static boolean isImpendingDoom = false;
	private static boolean beyondInitialization = false;

	public static void colonyReset() {
	
		ageAverage = 0.0;
		ageSummation = 0.0;

		lifespanAverage = 0.0;
		lifespanSummation = 0.0;

		numberMale = 0;
		numberFemale = 0;
		numberBaby = 0;
		numberPregnant = 0;
	
	}

	public static void colonyInclude(Mouse mouse) {
		
		ageSummation += mouse.getAge();
		lifespanSummation += mouse.getDNA().getLifespan();

		if(mouse.getGender() == Gender.MALE) {
			numberMale++;
		} else {
			numberFemale++;
		}
		if(mouse.isBaby()){
			numberBaby++;
		}
		if(mouse.isPregnant()){
			numberPregnant++;
		}

	}

	public static void colonyReady() {
		ageAverage = ageSummation / Colony.getSize();
		lifespanAverage = lifespanSummation / Colony.getSize();

		setInitials();
	}

	private static void setInitials() {
		if(!beyondInitialization && MouseSim.getRuntime() == 1){
			initialLifespan = lifespanAverage;
			beyondInitialization = true;
		} 
	}

	private static void checkDoom() {
		if(numberMale == 0 && numberPregnant == 0 && !isImpendingDoom) {
			Stream.update("IMPENDING DOOM: No more males (and no one is pregant)!");
			isImpendingDoom = true;
		}
		if(numberFemale == 0 && !isImpendingDoom) {
			Stream.update("IMPENDING DOOM: No more females!");
			isImpendingDoom = true;
		}
	}

	public static void update() {
		if(!beyondInitialization) return;

		checkDoom();
		reportLifespan();
	}

	private static void reportLifespan() {
		if(MouseSim.getRuntime() % UPDATE_LIFESPAN != 0) return; // Update every X cycles

		double ls = getAverageLifespan();
		double ils = getInitialLifespan();

		if(Math.abs(ls-ils) < 1.0) return; // Update if interesting (delta of > 1)

		Stream.update((ils > ls)? 
			"Your colony is devolving. Initial average lifespan was " + ils + " now the average lifespan is " + ls :
			"Your colony is evolving! Initial average lifespan was " + ils + " now the average lifespan is " + ls);		
	}

	public static double getAverageAge() {
		return (Math.floor((ageAverage * 10)))/10;
	}

	public static double getAverageLifespan() {
		return (Math.floor((lifespanAverage * 100)))/100;
	}

	public static double getInitialLifespan() {
		return (Math.floor((initialLifespan * 100)))/100;
	}

	public static int getNumberMale() {
		return numberMale;
	}

	public static int getNumberFemale() {
		return numberFemale;
	}

	public static int getNumberBaby() {
		return numberBaby;
	}

	public static int getNumberPregnant() {
		return numberPregnant;
	}

	public static int getNumberDead() {
		return numberDead;
	}

	public static void incrementDead() {
		numberDead++;
	}

}