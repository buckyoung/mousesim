public class Statistics {

	private static final int UPDATE = 30;

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

	public static void colonyReset() {
		if(MouseSim.getRuntime() % UPDATE != 1) return;

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
		if(MouseSim.getRuntime() % UPDATE != 1) return;

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
		if(MouseSim.getRuntime() % UPDATE != 1) return;

		ageAverage = ageSummation / Colony.getSize();
		lifespanAverage = lifespanSummation / Colony.getSize();

		if(MouseSim.getRuntime() == 1){
			initialLifespan = lifespanAverage;
		}
		if(numberMale == 0 && !isImpendingDoom) {
			Stream.update("IMPENDING DOOM: No more males!");
			isImpendingDoom = true;
		}
		if(numberFemale == 0 && !isImpendingDoom) {
			Stream.update("IMPENDING DOOM: No more males!");
			isImpendingDoom = true;
		}

	}

	public static double getAverageAge() {
		return (Math.floor((ageAverage * 100)))/100;
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