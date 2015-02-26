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

	public Food getAnyFood() { //REDO: shoudlnt return it -- mouse should eat it in place, so it can eat just a bit of it
		if(containsAny(Food.class)) {
			for(Object o : container) {
				if(Food.class.isInstance(o)) {
					return (Food) remove(o);
				}
			}
		}

		return null;
	}

	public boolean hasFood() {
		return containsAny(Food.class);
	}

	private boolean containsAny(Class clazz) {
		for (Object o : container) {
			if (clazz.isInstance(o)) {
				return true;
			}
		}
		return false;
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

}
