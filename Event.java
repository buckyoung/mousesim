import java.util.Comparator;

public class Event implements Comparable {

	private int fireTime;
	private EventFunction eventFunction;
	private Mouse mother = null;
	private Mouse father = null;
	private Mouse candidate1 = null;
	private Mouse candidate2 = null; 

	public Event(int time, EventFunction f,  Mouse m1, Mouse m2) {
		this.fireTime = MouseSim.getRuntime()+time;
		this.eventFunction = f;

		if(this.eventFunction == EventFunction.GIVE_BIRTH) {	
			this.father = m1;
			this.mother = m2;
		}

		if(this.eventFunction == EventFunction.ELECT_PRESIDENT) {
			this.candidate1 = m1;
			this.candidate2 = m2;
		}
	}

	public EventFunction getEventFunction() {
		return this.eventFunction;
	}

	public int getFireTime() {
		return this.fireTime;
	}

	public Mouse getFather() {
		return this.father;
	}

	public Mouse getMother() {
		return this.mother;
	}

	public Mouse getWinningCandidate() {
		return (this.candidate1.isAlive())? candidate1 : candidate2;
	}

	@Override
	public int compareTo(Object y){
		if (this.getFireTime() < ((Event)y).getFireTime()) {
			return -1;
		}
		if (this.getFireTime() > ((Event)y).getFireTime()) {
			return 1;
		}
		return 0;
	}

}