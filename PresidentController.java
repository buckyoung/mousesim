public class PresidentController {

	private static final int ELECTION_CYCLE = 179;
	private static final double ELECTION_POPULATION_MIN= 0.50;

	private static Mouse president = null;
	private static Mouse candidate1 = null;
	private static Mouse candidate2 = null;

	public static void update() {
		if(MouseSim.getRuntime() % ELECTION_CYCLE != 0) return;

		if((((double)Colony.getSize())/((double)MouseSim.getMaxMice())) < ELECTION_POPULATION_MIN) return; //demand elections when above 50% population

		if(hasPresident()) return;

		candidate1 = Colony.getCandidate(null);
		candidate2 = Colony.getCandidate(candidate1);

		if(candidate1 == null || candidate2 == null) return; //error

		Stream.update("The colony demands elections! Two candidates have been chosen.");

		Stream.update("Candidate 1: "+candidate1);
		Stream.update("Candidate 2: "+candidate2);

		Scheduler.addEvent(new Event(50, EventFunction.ELECT_PRESIDENT, candidate1, candidate2));

	}

	public static boolean hasPresident() {
		return president != null && president.isAlive();
	}

	public static void setPresident(Mouse m) {
		president = m;
	} 

	public static Mouse getPresident() {
		return hasPresident() ? president : null;
	}

}