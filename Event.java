import java.util.Comparator;

public class Event implements Comparator<Event> {

	private int fireTime;
	private EventFunction eventFunction;
	private Mouse mother;
	private Mouse father;

	public Event(int time, EventFunction f,  Mouse father, Mouse mother) {
		this.fireTime = time;
		this.eventFunction = f;
		this.mother = mother;
		this.father = father;
	}

	public EventFunction getEventFunction() {
		return this.eventFunction;
	}

	public int getFireTime() {
		return this.fireTime;
	}

	public Mouse getMother() {
		return this.mother;
	}

	public Mouse getFather() {
		return this.father;
	}

	@Override
	public int compare(Event x, Event y){
		if (x.getFireTime() < y.getFireTime()) {
			return -1;
		}
		if (x.getFireTime() > y.getFireTime()) {
			return 1;
		}
		return 0;
	}

}