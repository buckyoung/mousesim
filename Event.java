import java.util.Comparator;

public class Event implements Comparable {

	private int fireTime;
	private EventFunction eventFunction;
	private Mouse mother;
	private Mouse father;

	public Event(int time, EventFunction f,  Mouse father, Mouse mother) {
		this.fireTime = MouseSim.getRuntime()+time;
		this.eventFunction = f;
		this.father = father;
		this.mother = mother;	
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