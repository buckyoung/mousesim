import java.util.ArrayList;

public class WorldNode {

	//* Private Constants
	private static final String IMG_EMPTY = ".";
	private static final String IMG_FOOD = "f";
	private static final String IMG_MOUSE = "M";

	//* Private Fields
	private String representation;
	private ArrayList<Object> container;

	//* Public Methods
	public WorldNode() {
		representation = IMG_EMPTY;
		container = new ArrayList<>();
	}

	public void add(Food x) {
		if(x == null) return;

		container.add(x);
		this.representation = IMG_FOOD;
	}

	public void add(Mouse x) {
		if(x == null) return;

		container.add(x);
		this.representation = IMG_MOUSE;
	}

	public Food getAnyFood() {
		if(hasFood()) {
			for(Object o : container) {
				if(Food.class.isInstance(o)) {
					return (Food) o;
				}
			}
		}
		return null;
	}

	public boolean hasFood() {
		return containsAny(Food.class);
	}

	public Object remove(Object o) {
		if(container.contains(o)){
			container.remove(o);
			return o;
		}
		return null;
	}

	@Override
	public String toString() {
		if(container.isEmpty()) return IMG_EMPTY;

		if(containsAny(Mouse.class)) return IMG_MOUSE;

		if(containsAny(Food.class)) return IMG_FOOD;

		return representation;
	}

	//* Private Methods
	private boolean containsAny(Class clazz) {
		for (Object o : container) {
			if (clazz.isInstance(o)) {
				return true;
			}
		}
		return false;
	}

}
