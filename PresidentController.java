public class PresidentController {

	private static final int ELECTION_CYCLE = 5000;
	private static final double ELECTION_POPULATION_MIN= 0.50;

	public static void update() {
		if(MouseSim.getRuntime() % ELECTION_CYCLE != 0) return;

		if(Colony.getSize()/MouseSim.getMaxMice() < ELECTION_POPULATION_MIN) return; //demand elections when above 50% population

	}



}