import java.util.PriorityQueue;

public class Scheduler {

	private static PriorityQueue<Event> events = new PriorityQueue<>();

	public static void update() {
		if(events.size() == 0) return;

		Event e;

		while(events.size() > 0) {
			if(events.peek().getFireTime() == MouseSim.getRuntime()) {
				e = events.poll();

				switch(e.getEventFunction()) {
					case GIVE_BIRTH:
						e.getMother().giveBirth(e.getFather());
					break;

					case ELECT_PRESIDENT:
						Mouse m = e.getWinningCandidate();
						m.makePresident();
						PresidentController.setPresident(m);
					break;

					default:
				}

			} else {
				return;
			}
		}	
	}

	public static void addEvent(Event e) {
		events.add(e);
	}

}