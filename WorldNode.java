import java.util.ArrayList;

public class WorldNode {

	private static final String IMG_EMPTY = ".";
	private static final String IMG_MOUSE = "M";
	private static final String IMG_FOOD = "f";

	private String representation;

	private ArrayList<Object> container;

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

	private boolean containsAny(Class clazz) {
		for (Object e : container) {
			if (clazz.isInstance(e)) {
				return true;
			}
		}
		return false;
	}

	public void remove(Object o) {
		if(container.contains(o)){
			container.remove(o);
		}
	}

	@Override
	public String toString() {
		if(container.isEmpty()) return IMG_EMPTY;

		if(containsAny(Mouse.class)) return IMG_MOUSE;

		if(containsAny(Food.class)) return IMG_FOOD;

		return representation;
	}

}
